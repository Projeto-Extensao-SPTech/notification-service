package com.notification.service.infrastructure.queue.listener;

public record NotificationInstantEventMessage(
        String notificationType,
        String recipientEmail,
        String message
) {}