package com.gi.ro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API arbre généalogique")
                        .description("Cette API fournit des services de base pour la gestion des arbres généaloques tel que la création, la recherche ou le tri des arbres généalogiques.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Recherche Opérationnelle")
                                .email("watchowilly@gmail.com"))
                        .license(new License().name("License").url("URL")));
    }
}
