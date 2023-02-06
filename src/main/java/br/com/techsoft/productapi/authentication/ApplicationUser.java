package br.com.techsoft.productapi.authentication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {
    private UUID id;
    private String name;
    private String email;
}
