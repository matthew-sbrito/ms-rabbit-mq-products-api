package br.com.techsoft.productapi.modules.product;

import br.com.techsoft.productapi.core.exception.HttpException;
import br.com.techsoft.productapi.core.exception.ProductStockValidateException;
import br.com.techsoft.productapi.modules.category.Category;
import br.com.techsoft.productapi.modules.category.CategoryService;
import br.com.techsoft.productapi.modules.product.dto.*;
import br.com.techsoft.productapi.modules.supplier.Supplier;
import br.com.techsoft.productapi.modules.supplier.SupplierService;
import br.com.techsoft.productapi.modules.sales.dto.SalesConfirmationDTO;
import br.com.techsoft.productapi.modules.sales.rabbitmq.SalesConfirmationSender;
import br.com.techsoft.productapi.modules.sales.dto.SalesStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
    private final static Integer ZERO = 0;
    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;
    private final SalesConfirmationSender salesConfirmationSender;

    public Product find(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format("Product with id '%s' not found!", id)));
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.of(find(id));
    }

    public List<ProductResponse> findAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        return productRepository
                .findAll(sort)
                .stream()
                .map(ProductResponse::of)
                .toList();
    }

    public ProductResponse save(ProductRequest request) {
        validateProductArgs(request);

        Category category = categoryService.find(request.getCategoryId());
        Supplier supplier = supplierService.find(request.getSupplierId());

        Product product = productRepository.save(Product.of(request, category, supplier));

        return ProductResponse.of(product);
    }

    private void validateProductArgs(ProductRequest request) {
        if (isEmpty(request.getName()))
            throw new HttpException(HttpStatus.BAD_REQUEST, "The product name isn't valid!");

        if (isEmpty(request.getQuantityAvailable()) || request.getQuantityAvailable() < ZERO)
            throw new HttpException(HttpStatus.BAD_REQUEST, "The product quantity available isn't valid!");

        if (isEmpty(request.getSupplierId()))
            throw new HttpException(HttpStatus.BAD_REQUEST, "The supplier id isn't valid!");

        if (isEmpty(request.getCategoryId()))
            throw new HttpException(HttpStatus.BAD_REQUEST, "The category id isn't valid!");
    }


    public ProductResponse update(Long id, ProductRequest request) {
        Product product = find(id);

        product.setName(request.getName());

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    public void delete(Long id) {
        Product product = find(id);

        productRepository.delete(product);
    }

    public void updateProductStock(ProductStockDTO productStockDTO) {
        SalesConfirmationDTO message = new SalesConfirmationDTO();
        message.setSalesId(productStockDTO.getSalesId());

        try {
            validateStockUpdateData(productStockDTO);
            updateAndSaveProductStock(productStockDTO);

            message.setStatus(SalesStatus.APPROVED);
        } catch (Exception ex) {
            log.error("Error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            message.setStatus(SalesStatus.REJECT);
        }

        salesConfirmationSender.sendSalesConfirmationMessage(message);
    }

    private void validateStockUpdateData(ProductStockDTO productStockDTO) {
        if (isEmpty(productStockDTO) || isEmpty(productStockDTO.getSalesId()))
            throw new ProductStockValidateException("The sales id must be informed!");

        if (isEmpty(productStockDTO.getProducts()))
            throw new ProductStockValidateException("The products must be informed!");
    }

    private void updateAndSaveProductStock(ProductStockDTO productStockDTO) throws ProductStockValidateException {
        List<Product> products = productStockDTO
                .getProducts()
                .stream()
                .map(this::updateQuantityProduct)
                .toList();

        productRepository.saveAll(products);
    }

    private Product updateQuantityProduct(ProductQuantityDTO productQuantityDTO) throws ProductStockValidateException {
        Product product = checkProductStock(productQuantityDTO);

        product.decrementStock(productQuantityDTO.getQuantity());

        return product;
    }

    public void checkStock(CheckStockRequest request) {
        request
                .getProducts()
                .forEach(this::checkProductStock);
    }

    private Product checkProductStock(ProductQuantityDTO productQuantityDTO) {
        Product product = find(productQuantityDTO.getProductId());

        if (isEmpty(productQuantityDTO.getQuantity()))
            throw new ProductStockValidateException(String.format("The quantity of product with id %s must be informed!", product.getId()));

        if (productQuantityDTO.getQuantity() > product.getQuantityAvailable())
            throw new ProductStockValidateException(String.format("The quantity of product with id %s is big than quantity available!", product.getId()));

        return product;
    }
}
