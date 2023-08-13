package com.rainmaker.rainmaker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI rainmakerAPI(@Value("rainmaker.application.version") String appVersion) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("RainMaker API")
                                .description("RainMaker API 명세서")
                                .version(appVersion)
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Team RainMaker GitHub Organization")
                                .url("https://github.com/Inha-RainMaker")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "access-token",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
