package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.LogoutResult;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.auth.exception.InvalidTokenException;
import com.fivelogic_recreate.auth.exception.RefreshTokenNotFoundException;
import com.fivelogic_recreate.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Logout {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public LogoutResult execute(String refreshTokenStr) {
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

        if (!refreshToken.getUserId().value().equals(userId)) {
            throw new InvalidTokenException();
        }

        refreshToken.revoke();
        refreshTokenRepository.save(refreshToken);

        return new LogoutResult(true);
    }
}
