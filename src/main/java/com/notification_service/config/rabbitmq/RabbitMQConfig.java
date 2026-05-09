package com.notification_service.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MQ_MESSAGE_CONVERTER = "mq-message-converter";

    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";

    public static final String NOTIFICATION_SCHEDULED_QUEUE = "notification.scheduled.queue";
    public static final String NOTIFICATION_SCHEDULED_ROUTING_KEY = "notification.scheduled";

    public static final String NOTIFICATION_INSTANT_QUEUE = "notification.instant.queue";
    public static final String NOTIFICATION_INSTANT_ROUTING_KEY = "notification.instant";

    @Bean(MQ_MESSAGE_CONVERTER)
    public MessageConverter mqMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            @Qualifier(MQ_MESSAGE_CONVERTER) MessageConverter mqMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(mqMessageConverter);
        return factory;
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean(NOTIFICATION_SCHEDULED_QUEUE)
    public Queue notificationScheduledQueue() {
        return new Queue(NOTIFICATION_SCHEDULED_QUEUE, true);
    }

    @Bean
    public Binding notificationScheduledBinding(@Qualifier(NOTIFICATION_SCHEDULED_QUEUE) Queue notificationQueue, DirectExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_SCHEDULED_ROUTING_KEY);
    }

    @Bean(NOTIFICATION_INSTANT_QUEUE)
    public Queue notificationInstantQueue() {
        return new Queue(NOTIFICATION_INSTANT_QUEUE, true);
    }

    @Bean
    public Binding notificationInstantBinding(@Qualifier(NOTIFICATION_INSTANT_QUEUE) Queue notificationQueue, DirectExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_INSTANT_ROUTING_KEY);
    }
}