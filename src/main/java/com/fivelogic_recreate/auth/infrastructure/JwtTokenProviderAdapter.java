package com.fivelogic_recreate.auth.infrastructure;

import com.fivelogic_recreate.auth.application.TokenProvider;

import com.fivelogic_recreate.auth.application.dto.AuthTokens;
import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.Expiration;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderAdapter implements TokenProvider {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final long ACCESS_TOKEN_VALIDITY_MS = 3600000;
    private static final long REFRESH_TOKEN_VALIDITY_MS = 1209600000;

    @Override
    public AuthTokens issueTokens(String userId, String role) {
        String accessToken = createAccessToken(userId, role);
        RefreshToken refreshToken = createRefreshTokenEntity(userId);
        String refreshTokenJwt = createRefreshTokenJwt(userId, refreshToken.getId());

        return new AuthTokens(accessToken, refreshTokenJwt);
    }

    private String createAccessToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return jwtTokenProvider.createToken(userId, claims, ACCESS_TOKEN_VALIDITY_MS);
    }

    private RefreshToken createRefreshTokenEntity(String userId) {
        AuthUserId authUserId = new AuthUserId(userId);
        DeviceId deviceId = new DeviceId("UNKNOWN");
        Expiration expiration = new Expiration(
                LocalDateTime.now().plus(java.time.Duration.ofMillis(REFRESH_TOKEN_VALIDITY_MS)));

        RefreshToken refreshToken = RefreshToken.issue(authUserId, deviceId, expiration);
        return refreshTokenRepository.save(refreshToken);
    }

    private String createRefreshTokenJwt(String userId, Long tokenId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", tokenId); // Token ID를 JTI claim에 저장
        return jwtTokenProvider.createToken(userId, claims, REFRESH_TOKEN_VALIDITY_MS);
    }
}
