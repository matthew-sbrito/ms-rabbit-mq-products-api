package br.com.techsoft.productapi.modules.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private Integer quantityAvailable;
    private Long supplierId;
    private Long categoryId;
}
