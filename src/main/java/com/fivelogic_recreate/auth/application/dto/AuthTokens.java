package com.fivelogic_recreate.auth.application.dto;

public record AuthTokens(
        String accessToken,
        String refreshToken) {
}
