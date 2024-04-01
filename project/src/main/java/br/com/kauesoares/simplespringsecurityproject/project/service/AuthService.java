package br.com.kauesoares.simplespringsecurityproject.project.service;

import br.com.kauesoares.simplespringsecurityproject.project.dto.res.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    public LoginResponseDTO login(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }

}
