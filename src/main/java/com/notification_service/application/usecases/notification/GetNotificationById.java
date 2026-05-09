package com.notification_service.application.usecases.notification;

import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.entity.NotificationRecurrence;

import java.time.LocalDate;
import java.util.List;

public class GetNotificationById {

    private final NotificationGateway notificationGateway;

    public GetNotificationById(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public NotificationResponse execute(Long id) {

        Notification response = notificationGateway.findById(id);

        if (response == null) {
            throw new NotificationNotFoundException("A notificação com o id: %d não foi encontrada.".formatted(id));
        }

        List<LocalDate> recurrences = response.getNotificationRecurrences()
                .stream()
                .map(NotificationRecurrence::recurrence)
                .toList();

        return new NotificationResponse(
                response.getId(),
                response.getNotificationType(),
                response.getFairId(),
                response.getMessage(),
                response.getEventDate(),
                recurrences,
                response.getCreatedAt()
        );
    }
}
