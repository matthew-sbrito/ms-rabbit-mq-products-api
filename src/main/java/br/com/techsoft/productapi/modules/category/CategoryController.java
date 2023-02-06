package br.com.techsoft.productapi.modules.category;

import br.com.techsoft.productapi.modules.category.dto.CategoryRequest;
import br.com.techsoft.productapi.modules.category.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> find() {
        List<CategoryResponse> categoryResponseList = categoryService.findAll();

        return ResponseEntity
                .ok(categoryResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> findOne(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.findById(id);

        return ResponseEntity
                .ok(categoryResponse);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.save(request);

        return ResponseEntity
                .created(URI.create(String.format("/api/category/%s", categoryResponse.getId())))
                .body(categoryResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.update(id, request);

        return ResponseEntity
                .ok(categoryResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseEntity
                .ok()
                .build();
    }
}
