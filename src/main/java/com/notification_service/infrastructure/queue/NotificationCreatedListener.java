package com.notification_service.infrastructure.queue;

import com.notification_service.application.usecases.notification.CreateNotification;
import com.notification_service.config.rabbitmq.RabbitMQConfig;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.entity.NotificationRecurrence;
import com.notification_service.domain.enums.NotificationType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class NotificationCreatedListener {

    private final CreateNotification createNotification;

    public NotificationCreatedListener(CreateNotification createNotification) {
        this.createNotification = createNotification;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void listenNotificationCreatedEvent(NotificationCreatedEventMessage event) {
        List<NotificationRecurrence> recurrences = event.recurrence().stream()
                .map(NotificationRecurrence::new)
                .toList();

        Notification notification = new Notification(
                null,
                NotificationType.valueOf(event.notificationType()),
                event.fairId(),
                recurrences,
                event.message(),
                event.eventDate(),
                ZonedDateTime.now()
        );

        createNotification.execute(notification);
    }
}