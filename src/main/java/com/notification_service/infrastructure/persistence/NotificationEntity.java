package com.notification_service.infrastructure.persistence;

import com.notification_service.domain.entity.NotificationRecurrence;
import com.notification_service.domain.enums.NotificationType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "fair_id")
    private Long fairId;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE)
    private List<NotificationRecurrence> notificationRecurrence = new ArrayList<>();

    private String message;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    private  ZonedDateTime createdAt;

    public NotificationEntity(NotificationType notificationType, Long fairId, List<NotificationRecurrence> notificationRecurrence, String message, LocalDate eventDate, ZonedDateTime createdAt) {
        this.notificationType = notificationType;
        this.fairId = fairId;
        this.notificationRecurrence = notificationRecurrence;
        this.message = message;
        this.eventDate = eventDate;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public String getMessage() {
        return message;
    }

    public Long getFairId() {
        return fairId;
    }

    public List<NotificationRecurrence> getNotificationRecurrence() {
        return notificationRecurrence;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
