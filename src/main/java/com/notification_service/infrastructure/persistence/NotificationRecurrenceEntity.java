package com.notification_service.infrastructure.persistence;

import jakarta.persistence.*;

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

    protected NotificationRecurrenceEntity(){}

    public NotificationRecurrenceEntity(LocalDate recurrence) {
        this.recurrence = recurrence;
    }

    public void setNotification(NotificationEntity notification) {
        this.notification = notification;
    }

    public LocalDate getRecurrence() {
        return recurrence;
    }

}
