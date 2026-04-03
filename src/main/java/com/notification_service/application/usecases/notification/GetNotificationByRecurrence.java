package com.notification_service.application.usecases.notification;

import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.entity.NotificationRecurrence;

import java.time.LocalDate;
import java.util.List;

public class GetNotificationByRecurrence {
    private NotificationGateway notificationGateway;

    public GetNotificationByRecurrence(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public List<NotificationResponse> execute(LocalDate date) {
        var response = notificationGateway.findAll();

        if (response.isEmpty()) {
            throw new NotificationNotFoundException("Não foi encontrada nenhuma notificação para consultar a recorrência.");
        }

        var notificationRecurrence = notificationGateway.findNotificationRecurrences(date);

        return notificationRecurrence.stream()
                .map(this::toResponse)
                .toList();
    }

    private NotificationResponse toResponse(com.notification_service.domain.entity.Notification notification) {
        List<LocalDate> recurrences = notification.notificationRecurrences().stream()
                .map(NotificationRecurrence::recurrence)
                .toList();

        return new NotificationResponse(
                notification.id(),
                notification.notificationType(),
                notification.fairId(),
                notification.message(),
                notification.eventDate(),
                recurrences,
                notification.createdAt()
        );
    }
}
