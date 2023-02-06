package br.com.techsoft.productapi.configuration;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RabbitMQConfiguration {
    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;
    @Value("${app-config.rabbit.routingKey.product-stock}")
    private String productStockKey;
    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;
    @Value("${app-config.rabbit.queue.product-stock}")
    private String productStockQueue;
    @Value("${app-config.rabbit.queue.sales-confirmation}")
    private String salesConfirmationQueue;

    @Bean
    public TopicExchange productTopicExchange() {
        return new TopicExchange(productTopicExchange);
    }

    @Bean
    public Queue productStockQueue() {
        return new Queue(productStockQueue, true);
    }

    @Bean
    public Queue salesConfirmationQueue() {
        return new Queue(salesConfirmationQueue, true);
    }

    @Bean
    public Binding productStockQueueBinding(Queue productStockQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(productStockQueue)
                .to(topicExchange)
                .with(productStockKey);
    }

    @Bean
    public Binding salesConfirmationQueueBinding(Queue salesConfirmationQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(salesConfirmationQueue)
                .to(topicExchange)
                .with(salesConfirmationKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
