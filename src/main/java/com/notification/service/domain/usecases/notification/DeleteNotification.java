package com.notification.service.domain.usecases.notification;

import com.notification.service.domain.exception.notification.NotificationNotFoundException;
import com.notification.service.domain.gateway.NotificationRepositoryGateway;

public class DeleteNotification {

    private final NotificationRepositoryGateway notificationRepositoryGateway;

    public DeleteNotification(NotificationRepositoryGateway notificationRepositoryGateway) {
        this.notificationRepositoryGateway = notificationRepositoryGateway;
    }

    public void execute(Long id) {
        var response = notificationRepositoryGateway.findById(id);
        if (response == null) {
            throw new NotificationNotFoundException(
                    "A notificação com id: %d não foi encontrada e com isso não pode ser deletada.".formatted(id)
            );
        }
        notificationRepositoryGateway.delete(id);
    }
}
