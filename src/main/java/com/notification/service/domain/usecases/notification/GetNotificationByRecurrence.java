package com.notification.service.domain.usecases.notification;

import com.notification.service.application.dto.response.NotificationResponse;
import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.exception.notification.NotificationNotFoundException;
import com.notification.service.domain.gateway.NotificationRepositoryGateway;
import com.notification.service.domain.entity.NotificationRecurrence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class GetNotificationByRecurrence {
    private final NotificationRepositoryGateway notificationRepositoryGateway;

    public GetNotificationByRecurrence(NotificationRepositoryGateway notificationRepositoryGateway) {
        this.notificationRepositoryGateway = notificationRepositoryGateway;
    }

    public Page<NotificationResponse> execute(LocalDate date, Pageable pageable) {
        var response = notificationRepositoryGateway.findAll(pageable);

        if (response.isEmpty()) {
            throw new NotificationNotFoundException(
                    "Não foi encontrada nenhuma notificação para consultar a recorrência."
            );
        }

        var notificationRecurrence = notificationRepositoryGateway.findNotificationRecurrences(date, pageable);

        return notificationRecurrence.map(this::toResponse);

    }

    private NotificationResponse toResponse(Notification notification) {
        List<LocalDate> recurrences = notification.getNotificationRecurrences().stream()
                .map(NotificationRecurrence::recurrence)
                .toList();

        return new NotificationResponse(
                notification.getId(),
                notification.getNotificationType(),
                notification.getFairId(),
                notification.getMessage(),
                notification.getEventDate(),
                recurrences,
                notification.getCreatedAt()
        );
    }
}
