package br.com.kauesoares.simplespringsecurityproject.project.controller;

import br.com.kauesoares.simplespringsecurityproject.project.dto.Response;
import br.com.kauesoares.simplespringsecurityproject.project.dto.req.LoginRequestDTO;
import br.com.kauesoares.simplespringsecurityproject.project.dto.res.LoginResponseDTO;
import br.com.kauesoares.simplespringsecurityproject.project.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<Response<LoginResponseDTO>> login(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                Response.with(authService.login(authentication))
        );
    }

}
