package com.notification.service.infrastructure.queue.listener;

public record NotificationInstantEventMessage(
        String eventId,
        String notificationType,
        String recipientMailAddress,
        String message
) {}