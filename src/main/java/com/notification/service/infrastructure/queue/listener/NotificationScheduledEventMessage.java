package com.notification.service.infrastructure.queue.listener;

import java.time.LocalDate;
import java.util.List;

public record NotificationScheduledEventMessage(
        String eventId,
        String notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<Integer> recurrence
) {}