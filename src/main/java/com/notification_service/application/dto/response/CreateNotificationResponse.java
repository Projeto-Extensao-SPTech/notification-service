package com.notification_service.application.dto.response;

import com.notification_service.domain.enums.NotificationType;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public record CreateNotificationResponse(
        Long id,
        NotificationType notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<LocalDate> recurrence,
        ZonedDateTime createdAt
){}
