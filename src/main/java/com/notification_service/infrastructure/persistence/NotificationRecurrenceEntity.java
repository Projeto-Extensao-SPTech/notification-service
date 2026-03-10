package com.notification_service.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_recurrence_tb")
public class NotificationRecurrenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer recurrence;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationEntity notification;

    protected NotificationRecurrenceEntity(){}

    public NotificationRecurrenceEntity(Integer recurrence) {
        this.recurrence = recurrence;
    }

    public void setNotification(NotificationEntity notification) {
        this.notification = notification;
    }

    public Integer getRecurrence() {
        return recurrence;
    }

}
