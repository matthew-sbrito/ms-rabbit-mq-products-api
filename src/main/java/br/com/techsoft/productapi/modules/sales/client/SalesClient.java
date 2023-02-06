package br.com.techsoft.productapi.modules.sales.client;

import br.com.techsoft.productapi.modules.sales.dto.SalesProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(
        name = "${app-config.services.sales.name}",
        contextId = "${app-config.services.sales.name}",
        url = "${app-config.services.sales.url}"
)
public interface SalesClient {
    @GetMapping("/products/{productId}")
    ResponseEntity<SalesProductResponse> findSalesByProductId(@PathVariable Long productId);
}

