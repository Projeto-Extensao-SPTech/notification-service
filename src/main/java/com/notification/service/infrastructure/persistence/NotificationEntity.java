package com.notification.service.infrastructure.persistence;

import com.notification.service.domain.entity.NotificationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notification_tb")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "fair_id")
    private Long fairId;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationRecurrenceEntity> notificationRecurrence = new ArrayList<>();

    private String message;

    private String recipientMailAddress = null;

    @Column(name = "event_date")
    private LocalDate eventDate;

    private ZonedDateTime createdAt;

    protected NotificationEntity() {}

    public NotificationEntity(
            NotificationType notificationType,
            Long fairId,
            String message,
            String recipientMailAddress,
            LocalDate eventDate,
            ZonedDateTime createdAt
    ) {
        this.notificationType = notificationType;
        this.fairId = fairId;
        this.message = message;
        this.recipientMailAddress = recipientMailAddress;
        this.eventDate = eventDate;
        this.createdAt = createdAt;
    }

    public void addRecurrence(NotificationRecurrenceEntity recurrence) {
        recurrence.setNotification(this);
        this.notificationRecurrence.add(recurrence);
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

    public List<NotificationRecurrenceEntity> getNotificationRecurrence() {
        return notificationRecurrence;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getRecipientMailAddress() {
        return recipientMailAddress;
    }
}
