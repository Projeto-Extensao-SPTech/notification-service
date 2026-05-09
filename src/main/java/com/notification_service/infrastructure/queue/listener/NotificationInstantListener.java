package com.notification_service.infrastructure.queue.listener;

import com.notification_service.application.usecases.notification.CreateNotification;
import com.notification_service.config.rabbitmq.RabbitMQConfig;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.enums.NotificationType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class NotificationInstantListener {

    private final CreateNotification createNotification;

    public NotificationInstantListener(CreateNotification createNotification) {
        this.createNotification = createNotification;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_INSTANT_QUEUE)
    public void listerNotificationInstantEvent(NotificationInstantEventMessage event) {

        Notification notification = Notification.instant(
                NotificationType.valueOf(event.notificationType()),
                event.message(),
                event.recipientEmail()
        );

        createNotification.execute(notification, null);
    }
}
