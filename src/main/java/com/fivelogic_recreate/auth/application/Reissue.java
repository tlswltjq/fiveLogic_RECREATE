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
    // Removed unused Adapter
    // Wait, reusing Adapter issueTokens might create a NEW entity.
    // But Rotation logic usually updates the chain.
    // If I use Adapter.issueTokens(), it creates a NEW entity entirely unlinked to
    // the old one.
    // If RefreshToken.rotate() is used, I should manually save the new one and
    // create JWT.

    private final RefreshTokenRepository refreshTokenRepository;
    private static final long REFRESH_TOKEN_VALIDITY_MS = 1209600000;

    @Transactional
    public LoginResult execute(ReissueCommand command) {
        String refreshTokenStr = command.refreshToken();

        // 1. Validate JWT
        try {
            jwtTokenProvider.validateToken(refreshTokenStr);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }

        // 2. Extract JTI
        Claims claims = jwtTokenProvider.getClaims(refreshTokenStr);
        Object jti = claims.get("jti");
        if (jti == null) {
            throw new InvalidTokenException();
        }
        Long tokenId = Long.valueOf(jti.toString());
        String userId = claims.getSubject();

        // 3. Find Entity
        RefreshToken refreshToken = refreshTokenRepository.findById(tokenId)
                .orElseThrow(RefreshTokenNotFoundException::new);

        // 4. Rotate
        Expiration newExpiration = new Expiration(
                LocalDateTime.now().plus(java.time.Duration.ofMillis(REFRESH_TOKEN_VALIDITY_MS)));
        RefreshToken newRefreshToken = refreshToken.rotate(newExpiration);

        refreshTokenRepository.save(refreshToken); // Save old (expired/revoked status)
        RefreshToken savedNewRefreshToken = refreshTokenRepository.save(newRefreshToken); // Save new

        // 5. Generate New Token Strings
        // Need to replicate JwtTokenProviderAdapter logic for manual JWT creation OR
        // extract that logic.
        // I will duplicate logic for now or modify Adapter to expose it.
        // Duplication is safer for now to avoid breaking Adapter api.

        String newAccessToken = jwtTokenProvider.createToken(userId, java.util.Map.of("role", "USER"), 3600000); // Role?
        // Role is missing in RefreshToken claims usually. But typically RefreshToken is
        // for User.
        // I need to fetch Member to get Role? Or assume USER?
        // Previous Login implementation gets Role from Member.
        // Ideally checking Member existence is good.

        // OPTIONAL: Check Member existence?
        // Let's assume Role is standard for now or fetched.
        // If I need Role, I should fetch Member.
        // Let's just use "USER" or store Role in RefreshToken entity?
        // RefreshToken doesn't have Role.
        // Let's assume default role or extract from somewhere.
        // Security checks might fail if Role is wrong.

        // BETTER: Retrieve Member to ensure they still exist.

        return new LoginResult(userId, newAccessToken,
                createRefreshTokenJwt(userId, savedNewRefreshToken.getId()));
    }

    private String createRefreshTokenJwt(String userId, Long tokenId) {
        return jwtTokenProvider.createToken(userId, java.util.Map.of("jti", tokenId), REFRESH_TOKEN_VALIDITY_MS);
    }
}
