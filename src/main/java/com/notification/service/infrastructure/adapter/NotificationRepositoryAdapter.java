package com.notification.service.infrastructure.adapter;

import com.notification.service.domain.gateway.NotificationRepositoryGateway;
import com.notification.service.domain.entity.Notification;
import com.notification.service.infrastructure.mapper.NotificationEntityMapper;
import com.notification.service.infrastructure.persistence.NotificationEntity;
import com.notification.service.infrastructure.persistence.NotificationRecurrenceEntity;
import com.notification.service.infrastructure.persistence.NotificationRecurrenceRepository;
import com.notification.service.infrastructure.persistence.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Set;

public class NotificationRepositoryAdapter implements NotificationRepositoryGateway {

    private final NotificationRepository notificationRepository;

    private final NotificationRecurrenceRepository notificationRecurrenceRepository;

    private final NotificationEntityMapper notificationEntityMapper;

    public NotificationRepositoryAdapter(
            NotificationRepository notificationRepository,
            NotificationEntityMapper notificationEntityMapper,
            NotificationRecurrenceRepository notificationRecurrenceRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationEntityMapper = notificationEntityMapper;
        this.notificationRecurrenceRepository = notificationRecurrenceRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        NotificationEntity notificationEntity = notificationEntityMapper.toEntityNotification(notification);
        NotificationEntity savedNotification = notificationRepository.save(notificationEntity);
        return notificationEntityMapper.toDomainNotication(savedNotification);
    }

    @Override
    public void createRecurrences(Notification notification, Set<LocalDate> recurrences) {

        NotificationEntity notificationEntity = notificationRepository
                .findById(notification.getId())
                .orElseThrow();

        recurrences.forEach(date -> {
            NotificationRecurrenceEntity entity = new NotificationRecurrenceEntity(notificationEntity, date);

            notificationRecurrenceRepository.save(entity);
        });
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .map(notificationEntityMapper::toDomainNotication)
                .orElse(null);
    }

    @Override
    public Page<Notification> findNotificationRecurrences(LocalDate date, Pageable pageable) {
        Page<NotificationRecurrenceEntity> response = notificationRecurrenceRepository.findByRecurrence(date, pageable);

        return response
                .map(NotificationRecurrenceEntity::getNotification)
                .map(notificationEntityMapper::toDomainNotication);

    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(notificationEntityMapper::toDomainNotication);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}
