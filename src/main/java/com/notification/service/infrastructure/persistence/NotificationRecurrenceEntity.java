package com.notification.service.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "notification_recurrence_tb")
public class NotificationRecurrenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate recurrence;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationEntity notification;

    protected NotificationRecurrenceEntity() {}

    public NotificationRecurrenceEntity(NotificationEntity notification, LocalDate recurrence) {
        this.notification = notification;
        this.recurrence = recurrence;
    }

    public NotificationRecurrenceEntity(LocalDate recurrence) {
        this.recurrence = recurrence;
    }

    public LocalDate getRecurrence() {
        return recurrence;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public void setNotification(NotificationEntity notification) {
        this.notification = notification;
    }
}
