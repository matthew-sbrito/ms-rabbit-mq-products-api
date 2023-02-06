package br.com.techsoft.productapi.bootstrap;

import br.com.techsoft.productapi.modules.category.Category;
import br.com.techsoft.productapi.modules.category.CategoryRepository;
import br.com.techsoft.productapi.modules.product.Product;
import br.com.techsoft.productapi.modules.product.ProductRepository;
import br.com.techsoft.productapi.modules.supplier.Supplier;
import br.com.techsoft.productapi.modules.supplier.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor
public class Initializer {
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultData() {

        log.info("createDefaultData was called!");

        List<Category> categoryList = Stream.of("Comic Books", "Movies", "Books")
                .map(category -> Category.builder().description(category).build())
                .toList();

        categoryRepository.saveAll(categoryList);

        List<Supplier> supplierList = Stream.of("Panini Comics", "Amazon")
                .map(supplier -> Supplier.builder().name(supplier).build())
                .toList();

        supplierRepository.saveAll(supplierList);

        Product first = Product.builder()
                .name("Crise nas Infinitas Terras")
                .quantityAvailable(10)
                .supplier(supplierList.get(0))
                .category(categoryList.get(0))
                .build();

        Product second = Product.builder()
                .name("Interestelar")
                .quantityAvailable(5)
                .supplier(supplierList.get(1))
                .category(categoryList.get(1))
                .build();

        Product third = Product.builder()
                .name("Harry Potter e a Pedra Filosofal")
                .quantityAvailable(3)
                .supplier(supplierList.get(1))
                .category(categoryList.get(2))
                .build();

        productRepository.saveAll(List.of(first, second, third));

        log.info("createDefaultData done!");
    }

}
