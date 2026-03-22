package com.notification_service.infrastructure.gateway;

import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.entity.NotificationRecurrence;
import com.notification_service.infrastructure.persistence.NotificationEntity;
import com.notification_service.infrastructure.persistence.NotificationRecurrenceEntity;

public class NotificationEntityMapper {

    public NotificationEntity toEntity(Notification notification) {

        NotificationEntity notificationEntity = new NotificationEntity(
                notification.notificationType(),
                notification.fairId(),
                notification.message(),
                notification.eventDate(),
                notification.createdAt()
        );

        notification.notificationRecurrences()
                .forEach(notificationRecurrence -> {

                    NotificationRecurrenceEntity recurrenceEntity =
                            new NotificationRecurrenceEntity(
                                    notificationEntity,
                                    notificationRecurrence.recurrence()
                            );

                    notificationEntity.addRecurrence(recurrenceEntity);
                });

        return notificationEntity;
    }

    public Notification toDomain(NotificationEntity notificationEntity) {

        var recurrences = notificationEntity.getNotificationRecurrence()
                .stream()
                .map(this::toDomain)
                .toList();

        return new Notification(
                notificationEntity.getId(),
                notificationEntity.getNotificationType(),
                notificationEntity.getFairId(),
                recurrences,
                notificationEntity.getMessage(),
                notificationEntity.getEventDate(),
                notificationEntity.getCreatedAt()
        );
    }

    public static NotificationRecurrenceEntity toEntity(NotificationEntity notification, NotificationRecurrence notificationRecurrence) {
        return new NotificationRecurrenceEntity(notification,notificationRecurrence.recurrence());
    }

    public NotificationRecurrence toDomain(NotificationRecurrenceEntity entity) {
        return new NotificationRecurrence(entity.getRecurrence());
    }
}
