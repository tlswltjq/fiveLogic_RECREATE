package com.fivelogic_recreate.auth.domain;

public record AuthToken(
        Long memberId,
        String accessToken,
        String refreshToken,
        String grantType,
        Long expiresIn
) {
}
