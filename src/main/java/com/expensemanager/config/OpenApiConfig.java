package com.expensemanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseManagerOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Manager API")
                        .description("Backend API for personal finance management")
                        .version("v1")
                        .contact(new Contact()
                                .name("Expense Manager")));
    }
}
