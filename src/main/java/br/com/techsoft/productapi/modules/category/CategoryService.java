package br.com.techsoft.productapi.modules.category;

import br.com.techsoft.productapi.core.exception.HttpException;
import br.com.techsoft.productapi.modules.category.dto.CategoryRequest;
import br.com.techsoft.productapi.modules.category.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category find(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format("Category with id '%s' not found!", id)));
    }

    public CategoryResponse findById(Long id) {
        return CategoryResponse.of(find(id));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .toList();
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryArgs(request);

        Category category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryArgs(CategoryRequest request) {
        if(ObjectUtils.isEmpty(request.getDescription()))
            throw new HttpException(HttpStatus.BAD_REQUEST, "The category description isn't valid!");
    }


    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = find(id);

        category.setDescription(request.getDescription());

        categoryRepository.save(category);

        return CategoryResponse.of(category);
    }

    public void delete(Long id) {
        Category category = find(id);

        categoryRepository.delete(category);
    }
}
