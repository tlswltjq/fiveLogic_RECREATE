package com.fivelogic_recreate.auth.infrastructure;

import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {
    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return jpaRefreshTokenRepository.save(refreshToken);
    }

    @Override
    public List<RefreshToken> findByAuthUserId(AuthUserId userId) {
        return jpaRefreshTokenRepository.findByUserId(userId);
    }

    @Override
    public Optional<RefreshToken> findByDeviceId(DeviceId deviceId) {
        return jpaRefreshTokenRepository.findByDeviceId(deviceId);
    }

    @Override
    public Optional<RefreshToken> findById(Long id) {
        return jpaRefreshTokenRepository.findById(id);
    }
}
