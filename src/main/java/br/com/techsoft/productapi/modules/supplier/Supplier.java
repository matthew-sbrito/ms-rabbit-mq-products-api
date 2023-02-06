package br.com.techsoft.productapi.modules.supplier;

import br.com.techsoft.productapi.core.EntityDomain;
import br.com.techsoft.productapi.modules.product.Product;
import br.com.techsoft.productapi.modules.supplier.dto.SupplierRequest;
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
public class Supplier extends EntityDomain {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "supplier")
    private Set<Product> products = new HashSet<>();

    public static Supplier of(SupplierRequest request) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(request, supplier);

        return supplier;
    }
}
