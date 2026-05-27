package com.notification.service.domain.entity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public class Notification {

    private Long id;

    private NotificationType notificationType;

    private Long fairId;

    private List<NotificationRecurrence> notificationRecurrences;

    private String eventId;

    private String message;

    private String recipientMailAddress;

    private LocalDate eventDate;

    private ZonedDateTime createdAt;

    private Notification() {}

    public static Notification scheduled(
            String eventId,
            NotificationType notificationType,
            Long fairId,
            String message,
            LocalDate eventDate,
            List<NotificationRecurrence> recurrences
    ) {
        Notification notification = new Notification();
        notification.eventId = eventId;
        notification.notificationType = notificationType;
        notification.fairId = fairId;
        notification.message = message;
        notification.eventDate = eventDate;
        notification.notificationRecurrences = recurrences;
        notification.recipientMailAddress = null;
        notification.createdAt = ZonedDateTime.now();
        return notification;
    }

    public static Notification instant(
            String eventId,
            NotificationType notificationType,
            String message,
            String recipientMailAddress) {
        Notification notification = new Notification();
        notification.eventId = eventId;
        notification.notificationType = notificationType;
        notification.fairId = null;
        notification.message = message;
        notification.notificationRecurrences = List.of();
        notification.recipientMailAddress = recipientMailAddress;
        notification.createdAt = ZonedDateTime.now();
        return notification;
    }

    public Notification(
            Long id,
            NotificationType notificationType,
            Long fairId,
            List<NotificationRecurrence> notificationRecurrences,
            String message,
            String recipientMailAddress,
            LocalDate eventDate,
            ZonedDateTime createdAt
    ) {
        this.id = id;
        this.notificationType = notificationType;
        this.fairId = fairId;
        this.notificationRecurrences = notificationRecurrences;
        this.message = message;
        this.recipientMailAddress = recipientMailAddress;
        this.eventDate = eventDate;
        this.createdAt = createdAt;
    }

    public Notification withoutMessage() {
        Notification notification = new Notification();
        notification.id = this.id;
        notification.eventId = this.eventId;
        notification.notificationType = this.notificationType;
        notification.fairId = this.fairId;
        notification.notificationRecurrences = this.notificationRecurrences;
        notification.recipientMailAddress = this.recipientMailAddress;
        notification.eventDate = this.eventDate;
        notification.createdAt = this.createdAt;
        notification.message = null;
        return notification;
    }

    public boolean isHtmlMessage() {
        return message != null &&
                (message.contains("<html") || message.contains("<!DOCTYPE"));
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notificationType=" + notificationType +
                ", fairId=" + fairId +
                ", eventId='" + eventId + '\'' +
                ", eventDate=" + eventDate +
                ", createdAt=" + createdAt +
                ", recurrences=" + (notificationRecurrences != null ? notificationRecurrences.size() : 0) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public Long getFairId() {
        return fairId;
    }

    public List<NotificationRecurrence> getNotificationRecurrences() {
        return notificationRecurrences;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipientMailAddress() {
        return recipientMailAddress;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEventId() {
        return eventId;
    }
}