package br.com.kauesoares.simplespringsecurityproject.project.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "integration.s3")
@Data
public class S3Properties {

    private String region;

    private String accessKey;

    private String secretKey;

    private String bucketName;

}
