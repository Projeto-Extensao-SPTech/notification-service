package com.notification.service.infrastructure.queue.listener;

import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.entity.NotificationType;
import com.notification.service.domain.usecases.notification.SendInstantNotification;
import com.notification.service.infrastructure.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationInstantListener {

    private final SendInstantNotification sendInstantNotification;

    public NotificationInstantListener(SendInstantNotification sendInstantNotification) {
        this.sendInstantNotification = sendInstantNotification;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_INSTANT_QUEUE)
    public void listerNotificationInstantEvent(NotificationInstantEventMessage event) {

        Notification notification = Notification.instant(
                event.eventId(),
                NotificationType.valueOf(event.notificationType()),
                event.message(),
                event.recipientMailAddress()
        );

        sendInstantNotification.execute(notification);
    }
}
