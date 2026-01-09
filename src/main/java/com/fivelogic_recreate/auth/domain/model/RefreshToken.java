package com.fivelogic_recreate.auth.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private AuthUserId userId;
    @Embedded
    private DeviceId deviceId;
    @Embedded
    private Expiration expiration;
    @Enumerated(EnumType.STRING)
    private Status status;

    private RefreshToken(Long id, AuthUserId userId, DeviceId deviceId, Expiration expiration, Status status) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.expiration = expiration;
        this.status = status;
    }

    public static RefreshToken issue(AuthUserId userId, DeviceId deviceId, Expiration expiration) {
        return new RefreshToken(null, userId, deviceId, expiration, Status.ACTIVE);
    }

    public void expire() {
        if (this.status == Status.ACTIVE) {
            this.status = Status.EXPIRED;
        }
    }

    public void revoke() {
        this.status = Status.REVOKED;
    }

    public RefreshToken rotate(Expiration newExpiration) {
        if (this.status != Status.ACTIVE) {
            throw new IllegalStateException("유효하지 않은 토큰은 재발급할 수 없습니다.");
        }

        this.expire();

        return issue(userId, deviceId, newExpiration);
    }
}
