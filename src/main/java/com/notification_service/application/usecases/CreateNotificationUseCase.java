package com.notification_service.application.usecases;

import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;

public class CreateNotificationUseCase {

    private final NotificationGateway notificationGateway;

    public CreateNotificationUseCase(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public Notification createNotification(Notification notification){
        return  notificationGateway.createNotification(notification);
    }
}
