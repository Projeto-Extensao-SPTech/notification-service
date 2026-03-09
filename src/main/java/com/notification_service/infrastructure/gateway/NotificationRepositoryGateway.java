package com.notification_service.infrastructure.gateway;

import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;
import com.notification_service.infrastructure.persistence.NotificationEntity;
import com.notification_service.infrastructure.persistence.NotificationRepository;

public class NotificationRepositoryGateway implements NotificationGateway {

    private final NotificationRepository notificationRepository;
    private final NotificationEntityMapper notificationEntityMapper;

    public NotificationRepositoryGateway(NotificationRepository notificationRepository, NotificationEntityMapper notificationEntityMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationEntityMapper = notificationEntityMapper;
    }

    @Override
    public Notification createNotification(Notification notification) {
        NotificationEntity notificationEntity = notificationEntityMapper.toEntity(notification);
        NotificationEntity savedNotification = notificationRepository.save(notificationEntity);
        return notificationEntityMapper.toDomain(savedNotification);
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .map(notificationEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}
