package com.notification.service.application.dto;

import com.notification.service.application.dto.request.CreateNotificationRequest;
import com.notification.service.application.dto.request.SendInstantNotificationRequest;
import com.notification.service.application.dto.response.CreateNotificationResponse;
import com.notification.service.domain.entity.Notification;
import com.notification.service.domain.entity.NotificationRecurrence;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public class NotificationMapperDTO {
    public CreateNotificationResponse toResponse(Notification notification) {

        List<LocalDate> recurrences = notification.getNotificationRecurrences()
                .stream()
                .map(NotificationRecurrence::recurrence)
                .toList();

        return new CreateNotificationResponse(
                notification.getId(),
                notification.getNotificationType(),
                notification.getFairId(),
                notification.getMessage(),
                notification.getEventDate(),
                recurrences,
                ZonedDateTime.now()
        );
    }

    public Notification toDomain(CreateNotificationRequest request) {

        return new Notification(
                null,
                request.notificationType(),
                request.fairId(),
                List.of(),
                request.message(),
                null,
                request.eventDate(),
                ZonedDateTime.now()
        );
    }

    public Notification toDomain(SendInstantNotificationRequest request) {
        return Notification.instant(
                request.notificationType(),
                request.message(),
                request.recipientMailAddress()
        );
    }
}
