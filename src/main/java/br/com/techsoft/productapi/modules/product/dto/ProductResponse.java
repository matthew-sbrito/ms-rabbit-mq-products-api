package br.com.techsoft.productapi.modules.product.dto;

import br.com.techsoft.productapi.modules.category.dto.CategoryResponse;
import br.com.techsoft.productapi.modules.product.Product;
import br.com.techsoft.productapi.modules.supplier.dto.SupplierResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private Integer quantityAvailable;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    public static ProductResponse of(Product product) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .quantityAvailable(product.getQuantityAvailable())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(CategoryResponse.of(product.getCategory()))
                .supplier(SupplierResponse.of(product.getSupplier()))
                .build();
    }
}
