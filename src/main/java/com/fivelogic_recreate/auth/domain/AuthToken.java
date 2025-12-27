package com.fivelogic_recreate.auth.domain;

public record AuthToken(
        String accessToken,
        String refreshToken,
        String grantType,
        Long expiresIn) {
}
