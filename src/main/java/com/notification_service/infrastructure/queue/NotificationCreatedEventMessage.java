package com.notification_service.infrastructure.queue;

import java.time.LocalDate;
import java.util.List;

public record NotificationCreatedEventMessage(
        String notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<Integer> recurrence
) {}