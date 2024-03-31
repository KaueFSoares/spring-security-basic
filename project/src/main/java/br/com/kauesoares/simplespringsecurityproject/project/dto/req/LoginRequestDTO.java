package br.com.kauesoares.simplespringsecurityproject.project.dto.req;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDTO(
        @NotEmpty
        String username,

        @NotEmpty
        String password
) {
}
