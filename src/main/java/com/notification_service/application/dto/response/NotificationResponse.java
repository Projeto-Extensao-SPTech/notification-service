package com.notification_service.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notification_service.domain.enums.NotificationType;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public record NotificationResponse(
        Long id,
        @JsonProperty("type")
        NotificationType notificationType,
        @JsonProperty("fair_id")
        Long fairId,
        String message,
        @JsonProperty("event_date")
        LocalDate eventDate,
        List<LocalDate> recurrences,
        @JsonProperty("created_at")
        ZonedDateTime createdAt
) {
}
