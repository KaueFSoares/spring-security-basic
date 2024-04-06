package br.com.kauesoares.simplespringsecurityproject.project.dto.res;

public record AuthResponseDTO(
        String tokenType,
        String accessToken,
        Long accessExpiresIn,
        String refreshToken,
        Long refreshExpiresIn
) {
}
