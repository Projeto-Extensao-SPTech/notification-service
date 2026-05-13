package com.notification.service.application.dto.request;

import com.notification.service.domain.entity.NotificationType;

public record SendInstantNotificationRequest(
        NotificationType notificationType,
        String recipientMailAddress,
        String message
) {}
