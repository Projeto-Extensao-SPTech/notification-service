package com.notification.service.domain.entity;

public enum NotificationType {
    FAIR("Feira de adoção se aproximando"),
    DONATION("Chegou uma nova doação para o Abrigo Dog Feliz!"),
    GENERAL("Abrigo Dog Feliz quer falar com você"),
    VOLUNTEER("Chegou uma nova oportunidade de voluntariado!"),
    SPONSORSHIP("Chegou uma nova proposta de patrocinador!"),
    UPDATE_PASSWORD("Atualize sua senha");

    private final String description;
    public String getDescription() {
        return description;
    }

    NotificationType(String description) {
        this.description = description;
    }
}
