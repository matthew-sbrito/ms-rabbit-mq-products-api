package br.com.techsoft.productapi.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private Integer status;
    private String message;
    private String reason;
    private final String timestamp = LocalDateTime.now().toString();
}
