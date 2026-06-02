package com.notification.service.application.dto.response;

import com.notification.service.domain.entity.NotificationType;

public record SendInstantNotificationResponse(
        NotificationType notificationType,
        String message
) {}
