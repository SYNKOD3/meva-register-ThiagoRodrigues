package com.meva.finance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("meva-api")
                .packagesToScan("com.meva.finance.controller")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Meva Finance API")
                        .version("1.0")
                        .description("API para gestão de registros financeiros e usuários - Meva Finance")
                        .contact(new Contact()
                                .name("Thiago Rodrigues")
                                .email("charlesthiago1@hotmail.com")));
    }
}