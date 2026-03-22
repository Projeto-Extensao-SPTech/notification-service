package com.notification_service.application.gateway;

import com.notification_service.domain.entity.Notification;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface NotificationGateway {
    Notification createNotification(Notification notification);

    void createRecurrences(Notification notification, Set<LocalDate> recurrences);

    Notification findById(Long id);

    List<Notification> findAll();

    void delete(Long id);

}
