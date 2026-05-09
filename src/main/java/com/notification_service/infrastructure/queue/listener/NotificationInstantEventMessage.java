package com.notification_service.infrastructure.queue.listener;

public record NotificationInstantEventMessage(
        String notificationType,
        String recipientEmail,
        String message
) {}