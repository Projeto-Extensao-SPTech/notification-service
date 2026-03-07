package com.notification_service.domain.entity;

import com.notification_service.domain.enums.NotificationType;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public record Notification (
     Long id,
     NotificationType notificationType,
     Long fairId,
     List<NotificationRecurrence> notificationRecurrences,
     String message,
     LocalDate eventDate,
     ZonedDateTime createdAt
){}
