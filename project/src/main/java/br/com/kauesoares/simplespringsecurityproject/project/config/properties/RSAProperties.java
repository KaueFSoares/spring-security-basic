package br.com.kauesoares.simplespringsecurityproject.project.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "security.rsa")
@Data
public class RSAProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

}
