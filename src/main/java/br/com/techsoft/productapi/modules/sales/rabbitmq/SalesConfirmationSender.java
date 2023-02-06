package br.com.techsoft.productapi.modules.sales.rabbitmq;

import br.com.techsoft.productapi.configuration.RabbitMQConfiguration;
import br.com.techsoft.productapi.modules.sales.dto.SalesConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class SalesConfirmationSender {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfiguration rabbitMQConfiguration;
    private final ObjectMapper objectMapper;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message) {
        try {
            log.info("Send sales confirmation with json: {}", objectMapper.writeValueAsString(message));

            rabbitTemplate.convertAndSend(
                    rabbitMQConfiguration.getProductTopicExchange(),
                    rabbitMQConfiguration.getSalesConfirmationKey(),
                    message
            );

            log.info("Message was send successfully!");
        } catch (Exception ex) {
            log.error("Error while trying to send sales confirmation with error: {}", ex.getMessage(), ex);
        }
    }
}
