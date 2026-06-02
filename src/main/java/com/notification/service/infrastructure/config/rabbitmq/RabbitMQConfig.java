package com.notification.service.infrastructure.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitMQConfig {

    public static final String MQ_MESSAGE_CONVERTER = "mq-message-converter";

    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";

    public static final String NOTIFICATION_SCHEDULED_QUEUE = "notification.scheduled.queue";

    public static final String NOTIFICATION_SCHEDULED_ROUTING_KEY = "notification.scheduled";

    public static final String NOTIFICATION_INSTANT_QUEUE = "notification.instant.queue";

    public static final String NOTIFICATION_INSTANT_ROUTING_KEY = "notification.instant";

    public static final String NOTIFICATION_INSTANT_DLQ = "notification.instant.dlq";

    public static final String NOTIFICATION_INSTANT_DLX = "notification.instant.dlx";

    @Value("${rabbitmq.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${rabbitmq.retry.initial-interval:2000}")
    private long initialInterval;

    @Value("${rabbitmq.retry.multiplier:2.0}")
    private double multiplier;

    @Value("${rabbitmq.retry.max-interval:10000}")
    private long maxInterval;

    @Bean(MQ_MESSAGE_CONVERTER)
    public MessageConverter mqMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(
                rabbitTemplate,
                NOTIFICATION_INSTANT_DLX,
                NOTIFICATION_INSTANT_ROUTING_KEY
        );
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor(MessageRecoverer messageRecoverer) {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(maxAttempts)
                .backOffOptions(initialInterval, multiplier, maxInterval)
                .recoverer(messageRecoverer)
                .build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            @Qualifier(MQ_MESSAGE_CONVERTER) MessageConverter mqMessageConverter,
            RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(mqMessageConverter);
        factory.setAdviceChain(retryInterceptor);
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
    public Binding notificationScheduledBinding(
            @Qualifier(NOTIFICATION_SCHEDULED_QUEUE) Queue notificationQueue,
            DirectExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_SCHEDULED_ROUTING_KEY);
    }

    @Bean(NOTIFICATION_INSTANT_QUEUE)
    public Queue notificationInstantQueue() {
        return QueueBuilder.durable(NOTIFICATION_INSTANT_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_INSTANT_DLX)
                .withArgument("x-dead-letter-routing-key", NOTIFICATION_INSTANT_ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding notificationInstantBinding(
            @Qualifier(NOTIFICATION_INSTANT_QUEUE) Queue notificationQueue,
            DirectExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_INSTANT_ROUTING_KEY);
    }

    @Bean
    public DirectExchange notificationInstantDlx() {
        return new DirectExchange(NOTIFICATION_INSTANT_DLX);
    }

    @Bean
    public Queue notificationInstantDlq() {
        return new Queue(NOTIFICATION_INSTANT_DLQ, true);
    }

    @Bean
    public Binding notificationInstantDlqBinding() {
        return BindingBuilder
                .bind(notificationInstantDlq())
                .to(notificationInstantDlx())
                .with(NOTIFICATION_INSTANT_ROUTING_KEY);
    }
}