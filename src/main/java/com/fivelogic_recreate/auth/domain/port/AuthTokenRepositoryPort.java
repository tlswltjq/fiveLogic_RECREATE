package com.fivelogic_recreate.auth.domain.port;

import com.fivelogic_recreate.auth.domain.AuthToken;

import java.util.Optional;

public interface AuthTokenRepositoryPort {
    AuthToken save(AuthToken authToken);
    Optional<AuthToken> findByRefreshToken(String refreshToken);
    void deleteByMemberId(Long memberId);
    void delete(AuthToken authToken);
}