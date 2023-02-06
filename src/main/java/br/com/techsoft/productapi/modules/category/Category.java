package br.com.techsoft.productapi.modules.category;

import br.com.techsoft.productapi.core.EntityDomain;
import br.com.techsoft.productapi.modules.category.dto.CategoryRequest;
import br.com.techsoft.productapi.modules.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Category extends EntityDomain {
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public static Category of(CategoryRequest request) {
        var category = new Category();
        BeanUtils.copyProperties(request, category);
        return category;
    }
}
