package com.notification.service.domain.gateway;

import com.notification.service.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Set;

public interface NotificationRepositoryGateway {
    Notification createNotification(Notification notification);

    void createRecurrences(Notification notification, Set<LocalDate> recurrences);

    Notification findById(Long id);

    Page<Notification> findNotificationRecurrences(LocalDate date, Pageable pageable);

    Page<Notification> findAll(Pageable pageable);

    void delete(Long id);

}
