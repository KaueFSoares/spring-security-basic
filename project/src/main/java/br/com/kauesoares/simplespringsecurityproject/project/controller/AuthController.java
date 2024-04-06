package br.com.kauesoares.simplespringsecurityproject.project.controller;

import br.com.kauesoares.simplespringsecurityproject.project.dto.Response;
import br.com.kauesoares.simplespringsecurityproject.project.dto.res.AuthResponseDTO;
import br.com.kauesoares.simplespringsecurityproject.project.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<Response<AuthResponseDTO>> login(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                Response.with(this.authService.login(authentication))
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response<AuthResponseDTO>> refresh() {
        return ResponseEntity.ok(
                Response.with(this.authService.refresh())
        );
    }

}
