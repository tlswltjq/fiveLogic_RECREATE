package com.fivelogic_recreate.auth.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AuthToken {
    private final Long memberId;
    private final RefreshToken refreshToken;
    private final LocalDateTime expiresIn;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresIn);
    }
}
