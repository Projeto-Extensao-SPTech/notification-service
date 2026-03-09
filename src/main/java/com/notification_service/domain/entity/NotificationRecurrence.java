package com.notification_service.domain.entity;

import java.time.LocalDate;

public record NotificationRecurrence(
        LocalDate recurrence
) {}
