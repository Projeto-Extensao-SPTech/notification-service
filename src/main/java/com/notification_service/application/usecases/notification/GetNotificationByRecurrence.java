package com.notification_service.application.usecases.notification;

import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.NotificationRecurrence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class GetNotificationByRecurrence {
    private final NotificationGateway notificationGateway;

    public GetNotificationByRecurrence(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public Page<NotificationResponse> execute(LocalDate date, Pageable pageable) {
        var response = notificationGateway.findAll(pageable);

        if (response.isEmpty()) {
            throw new NotificationNotFoundException("Não foi encontrada nenhuma notificação para consultar a recorrência.");
        }

        var notificationRecurrence = notificationGateway.findNotificationRecurrences(date, pageable);

        return notificationRecurrence.map(this::toResponse);

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
