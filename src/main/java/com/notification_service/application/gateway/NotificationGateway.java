package com.notification_service.application.gateway;

import com.notification_service.domain.entity.Notification;

import java.util.List;

public interface NotificationGateway {
    Notification createNotification(Notification notification);

    Notification findById(Long id);

    List<Notification> findAll();

    void delete(Long id);

}
