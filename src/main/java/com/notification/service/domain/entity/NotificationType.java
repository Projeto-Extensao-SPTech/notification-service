package com.notification.service.domain.entity;

public enum NotificationType {
    FAIR(
            "Feira cadastrada com sucesso!",
            "Feira de adoção se aproximando"
    ),
    DONATION(
            "Chegou uma nova doação para o Abrigo Dog Feliz!",
            "Precisamos de doações!"
    ),
    GENERAL(
            "Abrigo Dog Feliz quer falar com você",
            "Abrigo Dog Feliz quer falar com você"
    ),
    VOLUNTEER(
            "Chegou uma nova oportunidade de voluntariado!",
            "Precisamos de voluntários!"
    ),
    SPONSORSHIP(
            "Chegou uma nova proposta de patrocinador!",
            "Precisamos de patrocinadores!"
    ),
    UPDATE_PASSWORD(
            "Atualize sua senha",
            "Atualize sua senha"
    );

    private final String toAdmin;

    private final String toCustomer;

    NotificationType(String toAdmin, String toCustomer) {
        this.toAdmin = toAdmin;
        this.toCustomer = toCustomer;
    }

    public String getToCustomer() {
        return toCustomer;
    }

    public String getToAdmin() {
        return toAdmin;
    }
}
