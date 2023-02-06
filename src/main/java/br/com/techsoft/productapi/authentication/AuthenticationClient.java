package br.com.techsoft.productapi.authentication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient(
        name = "${app-config.services.authentication.name}",
        contextId = "${app-config.services.authentication.name}",
        url = "${app-config.services.authentication.url}"
)
public interface AuthenticationClient {
    @GetMapping("/api/user/me")
    ResponseEntity<ApplicationUser> authenticate(@RequestHeader("Authorization") String authorizationHeader);
}
