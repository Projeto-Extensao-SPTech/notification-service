package com.notification_service.application.usecases.notification;

import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;

public class CreateNotification {

    private final NotificationGateway notificationGateway;

    public CreateNotification(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public Notification execute(Notification notification){
        return  notificationGateway.createNotification(notification);
    }
}
