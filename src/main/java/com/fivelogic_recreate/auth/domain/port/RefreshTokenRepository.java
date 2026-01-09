package com.fivelogic_recreate.auth.domain.port;

import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByAuthUserId(AuthUserId UserId);

    Optional<RefreshToken> findByDeviceId(DeviceId deviceId);
}
