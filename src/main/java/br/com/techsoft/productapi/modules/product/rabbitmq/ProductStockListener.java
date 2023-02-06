package br.com.techsoft.productapi.modules.product.rabbitmq;

import br.com.techsoft.productapi.modules.product.ProductService;
import br.com.techsoft.productapi.modules.product.dto.ProductStockDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductStockListener {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductMessage(ProductStockDTO product) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(product);
        log.info("Receive message JSON: [{}]", json);
        log.info("Receive products");

        productService.updateProductStock(product);
    }
}
