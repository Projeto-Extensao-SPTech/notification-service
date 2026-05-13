package com.notification.service.infrastructure.queue.listener;

public record NotificationInstantEventMessage(
        String notificationType,
        String recipientMailAddress,
        String message
) {}