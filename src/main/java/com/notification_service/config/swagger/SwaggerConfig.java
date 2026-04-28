package com.notification_service.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API De Notificações Abrigo Dog Feliz")
                        .version("1.0.0")
                        .description("Esta API disponibiliza o serviço de notificação do sistema Abrigo Dog Feliz, permitindo o gerenciamento de notificações no site.")
                        .contact(new Contact()
                                .name("SPTech School | Projeto Abrigo Dog Feliz")
                                .email("abrigodogfeliz@gmail.com"))
                        .license(new License()
                                .name("Licença Apache 2.0")
                                .url("http://springdoc.org")));
    }
}

