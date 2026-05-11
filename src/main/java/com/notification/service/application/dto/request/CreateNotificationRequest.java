package com.notification.service.application.dto.request;

import com.notification.service.domain.entity.NotificationType;
import java.time.LocalDate;
import java.util.List;

public record CreateNotificationRequest (
        NotificationType notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<Integer> recurrence
) {}