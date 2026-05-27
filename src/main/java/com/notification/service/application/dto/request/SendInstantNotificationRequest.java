package com.notification.service.application.dto.request;

import com.notification.service.domain.entity.NotificationType;

import java.util.UUID;

public record SendInstantNotificationRequest(
        String eventId,
        NotificationType notificationType,
        String recipientMailAddress,
        String message
) {
    public SendInstantNotificationRequest (
            NotificationType notificationType,
            String recipientMailAddress,
            String message
    ) {
        this(UUID.randomUUID().toString(), notificationType, recipientMailAddress, message);
    }
}
