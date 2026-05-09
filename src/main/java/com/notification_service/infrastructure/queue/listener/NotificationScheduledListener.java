package com.notification_service.infrastructure.queue.listener;

import com.notification_service.application.usecases.notification.CreateNotification;
import com.notification_service.config.rabbitmq.RabbitMQConfig;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.enums.NotificationType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class NotificationScheduledListener {

    private final CreateNotification createNotification;

    public NotificationScheduledListener(CreateNotification createNotification) {
        this.createNotification = createNotification;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_SCHEDULED_QUEUE)
    public void listerNotificationScheduledEvent(NotificationScheduledEventMessage event) {
        List<Integer> recurrences = event.recurrence();

        Notification notification = Notification.scheduled(
                NotificationType.valueOf(event.notificationType()),
                event.fairId(),
                event.message(),
                event.eventDate(),
                List.of()
        );

        createNotification.execute(notification, recurrences);
    }
}