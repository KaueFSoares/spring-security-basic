package br.com.kauesoares.simplespringsecurityproject.project.service;

import br.com.kauesoares.simplespringsecurityproject.project.config.properties.JwtProperties;
import br.com.kauesoares.simplespringsecurityproject.project.config.security.AuthUser;
import br.com.kauesoares.simplespringsecurityproject.project.dto.res.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;

    private static final String REFRESH_TOKEN_CLAIM = "refreshCode";

    public AuthResponseDTO generateAuthData(AuthUser authUser) {
        Instant now = Instant.now();
        Instant accessTokenExpiresAt = Instant.now().plus(1, ChronoUnit.HOURS);
        Instant refreshTokenExpiresAt = Instant.now().plus(1, ChronoUnit.DAYS);

        String scopes = authUser.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .collect(Collectors.joining(" "));

        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(accessTokenExpiresAt)
                .subject(authUser.getUsername())
                .claim("scope", scopes)
                .build();

        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(refreshTokenExpiresAt)
                .subject(authUser.getUsername())
                .claim("scope", scopes)
                .claim(REFRESH_TOKEN_CLAIM, authUser.getRefreshCode())
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();
        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();

        return new AuthResponseDTO(
                "Bearer",
                accessToken,
                accessTokenExpiresAt.toEpochMilli(),
                refreshToken,
                refreshTokenExpiresAt.toEpochMilli()
        );
    }

    public String extractRefreshCode(Jwt jwt) {
        return jwt.getClaim(REFRESH_TOKEN_CLAIM);
    }

}
