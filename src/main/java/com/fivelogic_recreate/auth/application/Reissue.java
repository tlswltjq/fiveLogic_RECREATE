package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.LoginResult;
import com.fivelogic_recreate.auth.application.dto.ReissueCommand;
import com.fivelogic_recreate.auth.domain.model.Expiration;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.auth.exception.InvalidTokenException;
import com.fivelogic_recreate.auth.exception.RefreshTokenNotFoundException;
import com.fivelogic_recreate.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Reissue {
    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;
    private static final long REFRESH_TOKEN_VALIDITY_MS = 1209600000;

    @Transactional
    public LoginResult execute(ReissueCommand command) {
        String refreshTokenStr = command.refreshToken();

        try {
            jwtTokenProvider.validateToken(refreshTokenStr);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }

        Claims claims = jwtTokenProvider.getClaims(refreshTokenStr);
        Object jti = claims.get("jti");
        if (jti == null) {
            throw new InvalidTokenException();
        }
        Long tokenId = Long.valueOf(jti.toString());
        String userId = claims.getSubject();

        RefreshToken refreshToken = refreshTokenRepository.findById(tokenId)
                .orElseThrow(RefreshTokenNotFoundException::new);

        Expiration newExpiration = new Expiration(
                LocalDateTime.now().plus(java.time.Duration.ofMillis(REFRESH_TOKEN_VALIDITY_MS)));
        RefreshToken newRefreshToken = refreshToken.rotate(newExpiration);

        refreshTokenRepository.save(refreshToken);
        RefreshToken savedNewRefreshToken = refreshTokenRepository.save(newRefreshToken);

        String newAccessToken = jwtTokenProvider.createToken(userId, java.util.Map.of("role", "USER"), 3600000);

        return new LoginResult(userId, newAccessToken,
                createRefreshTokenJwt(userId, savedNewRefreshToken.getId()));
    }

    private String createRefreshTokenJwt(String userId, Long tokenId) {
        return jwtTokenProvider.createToken(userId, java.util.Map.of("jti", tokenId), REFRESH_TOKEN_VALIDITY_MS);
    }
}
