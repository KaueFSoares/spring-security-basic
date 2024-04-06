package br.com.kauesoares.simplespringsecurityproject.project.service;

import br.com.kauesoares.simplespringsecurityproject.project.config.properties.JwtProperties;
import br.com.kauesoares.simplespringsecurityproject.project.dto.res.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;

    public LoginResponseDTO generateToken(Authentication authentication) {
        long EXPIRATION_IN_MINUTES = 10;
        long EXPIRATION_IN_SECONDS = 60 * EXPIRATION_IN_MINUTES;

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(EXPIRATION_IN_SECONDS);

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(authentication.getName())
                .claim("role", scopes)
                .build();

        return new LoginResponseDTO(
                "Bearer",
                jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
                expiresAt.toEpochMilli()
        );
    }


}
