package com.notification_service.application.usecases.notification;

import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.NotificationRecurrence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class GetNotification {
    private final NotificationGateway notificationGateway;

    public GetNotification(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public Page<NotificationResponse> execute(Pageable pageable) {
        var response = notificationGateway.findAll(pageable);

        if (response.isEmpty()) {
            throw new NotificationNotFoundException("Não foi encontrada nenhuma notificação.");
        }

        return response.map(this::toResponse);
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