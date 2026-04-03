package com.notification_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRecurrenceRepository extends JpaRepository<NotificationRecurrenceEntity, Long> {

    List<NotificationRecurrenceEntity> findByRecurrence(LocalDate date);
}
