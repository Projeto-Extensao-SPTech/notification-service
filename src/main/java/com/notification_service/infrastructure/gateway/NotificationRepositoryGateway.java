package com.notification_service.infrastructure.gateway;

import com.notification_service.application.gateway.NotificationGateway;
import com.notification_service.domain.entity.Notification;
import com.notification_service.infrastructure.persistence.NotificationEntity;
import com.notification_service.infrastructure.persistence.NotificationRecurrenceEntity;
import com.notification_service.infrastructure.persistence.NotificationRecurrenceRepository;
import com.notification_service.infrastructure.persistence.NotificationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class NotificationRepositoryGateway implements NotificationGateway {

    private final NotificationRepository notificationRepository;
    private final NotificationRecurrenceRepository notificationRecurrenceRepository;
    private final NotificationEntityMapper notificationEntityMapper;

    public NotificationRepositoryGateway(NotificationRepository notificationRepository, NotificationEntityMapper notificationEntityMapper, NotificationRecurrenceRepository notificationRecurrenceRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationEntityMapper = notificationEntityMapper;
        this.notificationRecurrenceRepository = notificationRecurrenceRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        NotificationEntity notificationEntity = notificationEntityMapper.toEntity(notification);
        NotificationEntity savedNotification = notificationRepository.save(notificationEntity);
        return notificationEntityMapper.toDomain(savedNotification);
    }

    @Override
    public void createRecurrences(Notification notification, Set<LocalDate> recurrences) {

        NotificationEntity notificationEntity = notificationRepository
                .findById(notification.id())
                .orElseThrow();

        recurrences.forEach(date -> {
            NotificationRecurrenceEntity entity = new NotificationRecurrenceEntity(notificationEntity, date);

            notificationRecurrenceRepository.save(entity);
        });
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .map(notificationEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}
