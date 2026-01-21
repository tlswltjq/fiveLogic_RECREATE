package com.fivelogic_recreate.auth.infrastructure;

import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findByUserId(AuthUserId userId);

    Optional<RefreshToken> findByDeviceId(DeviceId deviceId);
}
