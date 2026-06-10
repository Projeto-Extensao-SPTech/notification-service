package com.notification.service.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRecurrenceRepository extends JpaRepository<NotificationRecurrenceEntity, Long> {

    Page<NotificationRecurrenceEntity> findByRecurrence(LocalDate date, Pageable pageable);

    List<NotificationRecurrenceEntity> findByRecurrence(LocalDate date);
}
