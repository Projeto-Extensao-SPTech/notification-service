package com.notification.service.domain.usecases.notification;

import com.notification.service.application.dto.response.NotificationResponse;
import com.notification.service.domain.exception.notification.NotificationNotFoundException;
import com.notification.service.domain.gateway.NotificationRepositoryGateway;
import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.entity.NotificationRecurrence;

import java.time.LocalDate;
import java.util.List;

public class GetNotificationById {

    private final NotificationRepositoryGateway notificationRepositoryGateway;

    public GetNotificationById(NotificationRepositoryGateway notificationRepositoryGateway) {
        this.notificationRepositoryGateway = notificationRepositoryGateway;
    }

    public NotificationResponse execute(Long id) {

        Notification response = notificationRepositoryGateway.findById(id);

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
