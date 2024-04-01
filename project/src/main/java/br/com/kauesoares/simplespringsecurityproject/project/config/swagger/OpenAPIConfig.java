package br.com.kauesoares.simplespringsecurityproject.project.config.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenAPIConfig {
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String bearerAuth = "bearerAuth";

        OpenAPI openAPI = new OpenAPI();

        openAPI.info(new Info()
                .title("Simple Spring Security Project")
                .version("1.0")
                .description("Simple Spring Security Project"));

        openAPI.servers(List.of(
                new Server()
                        .url("http://localhost:8080")
                        .description("Localhost Server")
        ));

        openAPI.addSecurityItem(new SecurityRequirement()
                        .addList(bearerAuth))
                .components(new Components()
                        .addSecuritySchemes(bearerAuth, new SecurityScheme()
                                .name(bearerAuth)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

        return openAPI;
    }
}
