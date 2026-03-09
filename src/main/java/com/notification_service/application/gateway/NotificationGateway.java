package com.notification_service.application.gateway;

import com.notification_service.domain.entity.Notification;

public interface NotificationGateway {
    Notification createNotification(Notification notification);

    Notification findById(Long id);

    void delete(Long id);

}
