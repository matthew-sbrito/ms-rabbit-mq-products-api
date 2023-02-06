package br.com.techsoft.productapi.modules.product;

import br.com.techsoft.productapi.modules.category.Category;
import br.com.techsoft.productapi.core.EntityDomain;
import br.com.techsoft.productapi.modules.product.dto.ProductRequest;
import br.com.techsoft.productapi.modules.supplier.Supplier;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends EntityDomain {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer quantityAvailable;

    public static Product of(ProductRequest request, Category category, Supplier supplier) {
        return Product
                .builder()
                .name(request.getName())
                .quantityAvailable(request.getQuantityAvailable())
                .category(category)
                .supplier(supplier)
                .build();
    }

    public void decrementStock(Integer quantity) {
        quantityAvailable = quantityAvailable - quantity;
    }

    public void incrementStock(Integer quantity) {
        quantityAvailable = quantityAvailable + quantity;
    }

}
