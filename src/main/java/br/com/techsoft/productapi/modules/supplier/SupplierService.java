package br.com.techsoft.productapi.modules.supplier;

import br.com.techsoft.productapi.core.exception.HttpException;
import br.com.techsoft.productapi.modules.supplier.dto.SupplierRequest;
import br.com.techsoft.productapi.modules.supplier.dto.SupplierResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Supplier find(Long id) {
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format("Supplier with id '%s' not found!", id)));
    }

    public SupplierResponse findById(Long id) {
        return SupplierResponse.of(find(id));
    }

    public List<SupplierResponse> findAll() {
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .toList();
    }

    public SupplierResponse save(SupplierRequest request) {
        validateSupplierArgs(request);

        Supplier supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierArgs(SupplierRequest request) {
        if(ObjectUtils.isEmpty(request.getName()))
            throw new HttpException(HttpStatus.BAD_REQUEST, "The supplier name isn't valid!");
    }


    public SupplierResponse update(Long id, SupplierRequest request) {
        Supplier supplier = find(id);

        supplier.setName(request.getName());

        supplierRepository.save(supplier);

        return SupplierResponse.of(supplier);
    }

    public void delete(Long id) {
        Supplier supplier = find(id);

        supplierRepository.delete(supplier);
    }
}
