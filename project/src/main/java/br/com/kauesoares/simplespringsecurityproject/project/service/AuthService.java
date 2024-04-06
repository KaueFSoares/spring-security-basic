package br.com.kauesoares.simplespringsecurityproject.project.service;

import br.com.kauesoares.simplespringsecurityproject.project.config.security.AuthUser;
import br.com.kauesoares.simplespringsecurityproject.project.dto.res.AuthResponseDTO;
import br.com.kauesoares.simplespringsecurityproject.project.messages.Messages;
import br.com.kauesoares.simplespringsecurityproject.project.messages.exception.UnauthorizedException;
import br.com.kauesoares.simplespringsecurityproject.project.model.User;
import br.com.kauesoares.simplespringsecurityproject.project.repository.UserRepository;
import br.com.kauesoares.simplespringsecurityproject.project.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponseDTO login(Authentication authentication) {
        User user = this.userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException(Messages.USER_NOT_FOUND));

        user.setRefreshCode(UUID.randomUUID().toString());
        this.userRepository.save(user);

        AuthUser authUser = new AuthUser(user);

        return this.jwtService.generateAuthData(authUser);
    }

    public AuthResponseDTO refresh() {
        User user = this.userRepository.findByUsername(AuthUtil.getUserName())
                .orElseThrow(() -> new UnauthorizedException(Messages.USER_NOT_FOUND));

        Jwt jwt = AuthUtil.getJwt();

        String refreshCode = this.jwtService.extractRefreshCode(jwt);

        if (refreshCode == null)
            throw new UnauthorizedException(Messages.NOT_A_REFRESH_TOKEN);

        if (!refreshCode.equals(user.getRefreshCode()))
            throw new UnauthorizedException(Messages.INVALID_REFRESH_CODE);

        if (Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now()))
            throw new UnauthorizedException(Messages.EXPIRED_REFRESH_TOKEN);

        user.setRefreshCode(UUID.randomUUID().toString());

        this.userRepository.save(user);

        AuthUser authUser = new AuthUser(user);

        return this.jwtService.generateAuthData(authUser);
    }
}
