package com.notification_service.application.dto.request;

import com.notification_service.domain.enums.NotificationType;

import java.time.LocalDate;
import java.util.List;

public record CreateNotificationRequest (
        NotificationType notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<Integer> recurrence
){
}
