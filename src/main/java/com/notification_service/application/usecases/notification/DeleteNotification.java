package com.notification_service.application.usecases.notification;

import com.notification_service.application.exception.notification.NotificationNotFoundException;
import com.notification_service.application.gateway.NotificationGateway;

public class DeleteNotification {

    private final NotificationGateway notificationGateway;

    public DeleteNotification(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public void execute(Long id) {
        var response = notificationGateway.findById(id);
        if (response == null) {
            throw new NotificationNotFoundException("A notificação com id: %d não foi encontrada e com isso não pode ser deletada.".formatted(id));
        }
        notificationGateway.delete(id);
    }
}
