package br.com.techsoft.productapi.modules.product;

import br.com.techsoft.productapi.modules.product.dto.CheckStockRequest;
import br.com.techsoft.productapi.modules.product.dto.ProductRequest;
import br.com.techsoft.productapi.modules.product.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> find() {
        List<ProductResponse> productResponseList = productService.findAll();

        return ResponseEntity
                .ok(productResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> findOne(@PathVariable Long id) {
        ProductResponse productResponse = productService.findById(id);

        return ResponseEntity
                .ok(productResponse);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.save(request);

        return ResponseEntity
                .created(URI.create(String.format("/api/product/%s", productResponse.getId())))
                .body(productResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.update(id, request);

        return ResponseEntity
                .ok(productResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("check-stock")
    public ResponseEntity<?> checkStock(@RequestBody CheckStockRequest request) {
        productService.checkStock(request);

        return ResponseEntity
                .ok()
                .build();
    }
}
