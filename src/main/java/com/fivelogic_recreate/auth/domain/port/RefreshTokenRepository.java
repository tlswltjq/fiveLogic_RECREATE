package com.fivelogic_recreate.auth.domain.port;

import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    List<RefreshToken> findByAuthUserId(AuthUserId UserId);

    Optional<RefreshToken> findByDeviceId(DeviceId deviceId);
}
