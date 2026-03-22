package com.notification_service.application.usecases.notification;

import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;

public class GetNotificationById {

    private final NotificationGateway notificationGateway;

    public GetNotificationById(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public NotificationResponse execute(Long id) {

        var response = notificationGateway.findById(id);

        if (response == null) {
            throw new NotificationNotFoundException("A notificação com o id: %d não foi encontrada.".formatted(id));
        }

        return new NotificationResponse(
                response.id(),
                response.notificationType(),
                response.fairId(),
                response.message(),
                response.eventDate()
        );
    }
}
