package com.notification_service.application.dto.response;

import com.notification_service.domain.enums.NotificationType;

import java.time.LocalDate;

public record NotificationResponse(
        Long id,
        NotificationType notificationType,
        Long fairId,
        String message,
        LocalDate eventDate
) {}
