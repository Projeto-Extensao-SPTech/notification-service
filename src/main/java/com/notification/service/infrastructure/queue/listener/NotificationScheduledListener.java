package com.notification.service.infrastructure.queue.listener;

import com.notification.service.domain.usecases.notification.CreateNotification;
import com.notification.service.infrastructure.config.rabbitmq.RabbitMQConfig;
import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.entity.NotificationType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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