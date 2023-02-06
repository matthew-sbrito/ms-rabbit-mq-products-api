package br.com.techsoft.productapi.modules.supplier;

import br.com.techsoft.productapi.modules.supplier.dto.SupplierRequest;
import br.com.techsoft.productapi.modules.supplier.dto.SupplierResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/supplier")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> find() {
        List<SupplierResponse> supplierResponseList = supplierService.findAll();

        return ResponseEntity
                .ok(supplierResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<SupplierResponse> findOne(@PathVariable Long id) {
        SupplierResponse supplierResponse = supplierService.findById(id);

        return ResponseEntity
                .ok(supplierResponse);
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@RequestBody SupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.save(request);

        return ResponseEntity
                .created(URI.create(String.format("/api/supplier/%s", supplierResponse.getId())))
                .body(supplierResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<SupplierResponse> update(@PathVariable Long id, @RequestBody SupplierRequest request) {
        SupplierResponse supplierResponse = supplierService.update(id, request);

        return ResponseEntity
                .ok(supplierResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        supplierService.delete(id);

        return ResponseEntity
                .ok()
                .build();
    }
}
