package com.fivelogic_recreate.auth.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank(message = "Refresh Token이 필요합니다.") String refreshToken) {
}
