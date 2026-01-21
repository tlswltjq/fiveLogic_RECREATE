package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.LogoutResult;
import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.Expiration;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.auth.exception.InvalidTokenException;
import com.fivelogic_recreate.auth.support.TestClaims;
import com.fivelogic_recreate.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogoutTest {

    @InjectMocks
    private Logout logout;

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("로그아웃 성공")
    void logout_success() {
        // given
        String refreshTokenStr = "valid_token";
        Long tokenId = 1L;
        String userId = "user1";

        Claims claims = new TestClaims(Map.of("jti", tokenId, "sub", userId));
        given(jwtTokenProvider.getClaims(refreshTokenStr)).willReturn(claims);

        RefreshToken refreshToken = RefreshToken.issue(
                new AuthUserId(userId),
                new DeviceId("UNKNOWN"),
                new Expiration(LocalDateTime.now().plusDays(1)));
        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(refreshToken));

        // when
        LogoutResult result = logout.execute(refreshTokenStr);

        // then
        assertThat(result.success()).isTrue();
        // Check if revoked? (Needs getter or verify state change if possible, or verify
        // save called)
        verify(refreshTokenRepository).save(refreshToken);
    }

    @Test
    @DisplayName("토큰 주인이 다르면 실패")
    void logout_fail_userMismatch() {
        // given
        String refreshTokenStr = "valid_token";
        Long tokenId = 1L;
        String userId = "user1";
        String otherUser = "user2";

        Claims claims = new TestClaims(Map.of("jti", tokenId, "sub", otherUser)); // Claims say user2
        given(jwtTokenProvider.getClaims(refreshTokenStr)).willReturn(claims);

        RefreshToken refreshToken = RefreshToken.issue(
                new AuthUserId(userId), // DB says user1
                new DeviceId("UNKNOWN"),
                new Expiration(LocalDateTime.now().plusDays(1)));
        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(refreshToken));

        // when & then
        assertThatThrownBy(() -> logout.execute(refreshTokenStr))
                .isInstanceOf(InvalidTokenException.class);
    }
}
