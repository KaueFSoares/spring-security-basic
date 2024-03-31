package br.com.kauesoares.simplespringsecurityproject.project.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "security.auth.seed")
@Data
public class AuthProperties {

    private String adminUsername;
    private String adminPassword;

    private String userUsername;
    private String userPassword;

}
