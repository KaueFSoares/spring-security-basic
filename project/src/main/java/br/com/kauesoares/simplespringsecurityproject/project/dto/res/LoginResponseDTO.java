package br.com.kauesoares.simplespringsecurityproject.project.dto.res;

public record LoginResponseDTO(
        String tokenType,
        String accessToken,
        Long expiresIn
) {
}
