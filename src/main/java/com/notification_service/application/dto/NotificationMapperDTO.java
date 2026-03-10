package com.notification_service.application.dto;

import com.notification_service.application.dto.request.CreateNotificationRequest;
import com.notification_service.application.dto.response.CreateNotificationResponse;
import com.notification_service.domain.entity.Notification;
import com.notification_service.domain.entity.NotificationRecurrence;

import java.time.ZonedDateTime;
import java.util.List;

public class NotificationMapperDTO {
    public CreateNotificationResponse toResponse(Notification notification) {

        List<Integer> recurrences = notification.notificationRecurrences()
                .stream()
                .map(NotificationRecurrence::recurrence)
                .toList();

        return new CreateNotificationResponse(
                notification.id(),
                notification.notificationType(),
                notification.fairId(),
                notification.message(),
                notification.eventDate(),
                recurrences,
                ZonedDateTime.now()
        );
    }

   public Notification toDomain(CreateNotificationRequest request ){

       List<NotificationRecurrence> recurrences = request.recurrence()
               .stream()
               .map(NotificationRecurrence::new)
               .toList();

        return new Notification(
                null,
                request.notificationType(),
                request.fairId(),
                recurrences,
                request.message(),
                request.eventDate(),
                ZonedDateTime.now()
        );
    }
}
