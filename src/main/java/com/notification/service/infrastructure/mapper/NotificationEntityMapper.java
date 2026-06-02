package com.notification.service.infrastructure.mapper;

import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.entity.NotificationRecurrence;
import com.notification.service.infrastructure.persistence.NotificationEntity;
import com.notification.service.infrastructure.persistence.NotificationRecurrenceEntity;

public class NotificationEntityMapper {

    public NotificationEntity toEntityNotification(Notification notification) {

        NotificationEntity notificationEntity = new NotificationEntity(
                notification.getNotificationType(),
                notification.getFairId(),
                notification.getMessage(),
                notification.getRecipientMailAddress(),
                notification.getEventDate(),
                notification.getCreatedAt(),
                notification.getEventId()
        );

        notification.getNotificationRecurrences()
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

    public Notification toDomainNotication(NotificationEntity notificationEntity) {

        var recurrences = notificationEntity.getNotificationRecurrence()
                .stream()
                .map(this::toDomainRecurrence)
                .toList();

        return new Notification(
                notificationEntity.getId(),
                notificationEntity.getNotificationType(),
                notificationEntity.getFairId(),
                recurrences,
                notificationEntity.getMessage(),
                notificationEntity.getRecipientMailAddress(),
                notificationEntity.getEventDate(),
                notificationEntity.getCreatedAt()
        );
    }

    public static NotificationRecurrenceEntity toEntityRecurrence(
            NotificationEntity notification,
            NotificationRecurrence notificationRecurrence
    ) {
        return new NotificationRecurrenceEntity(notification, notificationRecurrence.recurrence());
    }

    public NotificationRecurrence toDomainRecurrence(NotificationRecurrenceEntity entity) {
        return new NotificationRecurrence(entity.getRecurrence());
    }
}
