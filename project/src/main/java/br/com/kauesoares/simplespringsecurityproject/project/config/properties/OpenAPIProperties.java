package br.com.kauesoares.simplespringsecurityproject.project.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "docs.openapi")
@Data
public class OpenAPIProperties {

    private String productionUrl;
    private String stageUrl;

}
