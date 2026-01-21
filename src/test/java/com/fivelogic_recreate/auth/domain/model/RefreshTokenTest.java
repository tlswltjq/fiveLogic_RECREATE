package com.fivelogic_recreate.auth.domain.model;

import com.fivelogic_recreate.auth.domain.model.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RefreshTokenTest {

    @Test
    @DisplayName("RefreshToken 발급 성공")
    void issue_success() {
        // given
        AuthUserId userId = new AuthUserId("user1");
        DeviceId deviceId = new DeviceId("device1");
        Expiration expiration = new Expiration(LocalDateTime.now().plusDays(14));

        // when
        RefreshToken refreshToken = RefreshToken.issue(userId, deviceId, expiration);

        // then
        assertThat(refreshToken).isNotNull();
        assertThat(refreshToken.getUserId()).isEqualTo(userId);
        assertThat(refreshToken.getDeviceId()).isEqualTo(deviceId);
        assertThat(refreshToken.getExpiration()).isEqualTo(expiration);
        assertThat(refreshToken.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    @DisplayName("RefreshToken 만료 처리")
    void expire_success() {
        // given
        RefreshToken refreshToken = RefreshToken.issue(
                new AuthUserId("user1"),
                new DeviceId("device1"),
                new Expiration(LocalDateTime.now().plusDays(14)));

        // when
        refreshToken.expire();

        // then
        assertThat(refreshToken.getStatus()).isEqualTo(Status.EXPIRED);
    }

    @Test
    @DisplayName("RefreshToken 취소(Revoke) 처리")
    void revoke_success() {
        // given
        RefreshToken refreshToken = RefreshToken.issue(
                new AuthUserId("user1"),
                new DeviceId("device1"),
                new Expiration(LocalDateTime.now().plusDays(14)));

        // when
        refreshToken.revoke();

        // then
        assertThat(refreshToken.getStatus()).isEqualTo(Status.REVOKED);
    }

    @Test
    @DisplayName("RefreshToken 갱신(Rotate) 성공")
    void rotate_success() {
        // given
        RefreshToken oldToken = RefreshToken.issue(
                new AuthUserId("user1"),
                new DeviceId("device1"),
                new Expiration(LocalDateTime.now().plusDays(14)));
        Expiration newExpiration = new Expiration(LocalDateTime.now().plusDays(30));

        // when
        RefreshToken newToken = oldToken.rotate(newExpiration);

        // then
        assertThat(oldToken.getStatus()).isEqualTo(Status.EXPIRED); // 기존 토큰은 만료됨
        assertThat(newToken).isNotNull();
        assertThat(newToken.getStatus()).isEqualTo(Status.ACTIVE); // 새 토큰은 활성 상태
        assertThat(newToken.getUserId()).isEqualTo(oldToken.getUserId());
        assertThat(newToken.getExpiration()).isEqualTo(newExpiration);
    }

    @Test
    @DisplayName("비활성 상태인 RefreshToken은 갱신 불가")
    void rotate_fail_inactive() {
        // given
        RefreshToken expiredToken = RefreshToken.issue(
                new AuthUserId("user1"),
                new DeviceId("device1"),
                new Expiration(LocalDateTime.now().plusDays(14)));
        expiredToken.expire();
        Expiration newExpiration = new Expiration(LocalDateTime.now().plusDays(30));

        // when & then
        assertThatThrownBy(() -> expiredToken.rotate(newExpiration))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("유효하지 않은 토큰은 재발급할 수 없습니다.");
    }
}
