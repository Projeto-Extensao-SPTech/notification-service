package com.notification_service.application.usecases.notification;

import com.notification_service.application.dto.response.NotificationResponse;
import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;

import java.util.List;

public class GetNotification {
    private final NotificationGateway notificationGateway;

    public GetNotification(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public List<NotificationResponse> execute() {
        var response = notificationGateway.findAll();

        if (response.isEmpty()) {
            throw new NotificationNotFoundException("Não foi encontrada nenhuma notificação.");
        }

        return response.stream()
                .map(notification -> new NotificationResponse(
                        notification.id(),
                        notification.notificationType(),
                        notification.fairId(),
                        notification.message(),
                        notification.eventDate()
                ))
                .toList();
    }
}