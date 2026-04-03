package com.notification_service.infrastructure.persistence;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

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

    public NotificationEntity getNotification(){
        return notification;
    }

    public void setNotification(NotificationEntity notificationEntity) {
    }
}
