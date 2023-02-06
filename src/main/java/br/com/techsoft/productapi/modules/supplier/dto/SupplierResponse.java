package br.com.techsoft.productapi.modules.supplier.dto;

import br.com.techsoft.productapi.modules.supplier.Supplier;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class SupplierResponse {
    private Long id;
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    public static SupplierResponse of(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);

        return response;
    }
}
