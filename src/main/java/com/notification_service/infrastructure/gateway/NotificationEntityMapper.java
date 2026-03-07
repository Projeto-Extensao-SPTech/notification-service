package com.notification_service.infrastructure.gateway;

import com.notification_service.domain.entity.Notification;
import com.notification_service.infrastructure.persistence.NotificationEntity;

public class NotificationEntityMapper {
    public NotificationEntity toEntity(Notification notificationDomainObj) {
        return new NotificationEntity(
                notificationDomainObj.notificationType(),
                notificationDomainObj.fairId(),
                notificationDomainObj.notificationRecurrences(),
                notificationDomainObj.message(),
                notificationDomainObj.eventDate(),
                notificationDomainObj.createdAt()
        );
    }

    public Notification toDomain(NotificationEntity notificationEntity){
        return new Notification(
                notificationEntity.getId(),
                notificationEntity.getNotificationType(),
                notificationEntity.getFairId(),
                notificationEntity.getNotificationRecurrence(),
                notificationEntity.getMessage(),
                notificationEntity.getEventDate(),
                notificationEntity.getCreatedAt()
        );
    }
}
