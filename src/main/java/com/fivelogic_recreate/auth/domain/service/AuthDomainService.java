package com.fivelogic_recreate.auth.domain.service;

import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.Expiration;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.auth.domain.port.TokenProvider;
import com.fivelogic_recreate.member.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDomainService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public RefreshToken issue(UserId userId, DeviceId deviceId) {
        AuthUserId authUserId = new AuthUserId(userId.value());

        Expiration expiration = tokenProvider.getExpiration();
        RefreshToken refreshTokenEntity = RefreshToken.issue(authUserId, deviceId, expiration);
        refreshTokenRepository.save(refreshTokenEntity);

        return refreshTokenEntity;
    }

    public void revokeByUserId(AuthUserId userId) {
        refreshTokenRepository.findByAuthUserId(userId)
                .forEach(RefreshToken::revoke);
    }

    public void revokeByDevice(DeviceId deviceId) {
        refreshTokenRepository.findByDeviceId(deviceId)
                .ifPresent(RefreshToken::revoke);
    }

    public RefreshToken rotate(RefreshToken token) {
        Expiration newExpiration = tokenProvider.getExpiration();
        return token.rotate(newExpiration);
    }
}
