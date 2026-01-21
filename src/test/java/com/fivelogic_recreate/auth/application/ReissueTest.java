package com.fivelogic_recreate.auth.application;

import com.fivelogic_recreate.auth.application.dto.LoginResult;
import com.fivelogic_recreate.auth.application.dto.ReissueCommand;
import com.fivelogic_recreate.auth.domain.model.AuthUserId;
import com.fivelogic_recreate.auth.domain.model.DeviceId;
import com.fivelogic_recreate.auth.domain.model.Expiration;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.auth.exception.InvalidTokenException;
import com.fivelogic_recreate.auth.exception.RefreshTokenNotFoundException;
import com.fivelogic_recreate.auth.infrastructure.JwtTokenProviderAdapter;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReissueTest {

    @InjectMocks
    private Reissue reissue;

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private JwtTokenProviderAdapter tokenProviderAdapter;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("유효한 리프레시 토큰으로 재발급 성공")
    void reissue_success() {
        // given
        String refreshTokenStr = "valid_refresh_token";
        String userId = "user1";
        Long tokenId = 1L;

        Claims claims = new TestClaims(Map.of("jti", tokenId, "sub", userId));

        given(jwtTokenProvider.getClaims(refreshTokenStr)).willReturn(claims);

        RefreshToken refreshToken = RefreshToken.issue(
                new AuthUserId(userId),
                new DeviceId("UNKNOWN"),
                new Expiration(LocalDateTime.now().plusDays(1)));
        // Reflection to set ID? Or assume mock repository returns it.
        // Since ID is generated, we can't easily set it on the object created via
        // factory without reflection or setter.
        // Assuming findById returns this object.

        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(refreshToken));

        given(refreshTokenRepository.save(any(RefreshToken.class))).willAnswer(invocation -> {
            RefreshToken saved = invocation.getArgument(0);
            try {
                java.lang.reflect.Field idField = saved.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(saved, 2L); // Set fake ID
            } catch (Exception e) {
                // Ignore
            }
            // Simulate ID generation for new token
            // Reflection or Mocking specific return?
            // Actually Reissue uses getId() from the *returned* Saved entity.

            // We need to ensure the SECOND save (new token) returns an object with ID.
            // But Mockito returns null by default for non-void, or last stubbing?
            return saved;
        });

        // Improve Mock: Distinguish calls?
        // First save is old token (update). Second save is new token (insert).
        // Let's rely on loose mocking for save, but ensure createToken is called.

        given(jwtTokenProvider.createToken(anyString(), anyMap(), anyLong())).willReturn("new_token");
        given(jwtTokenProvider.createToken(eq(userId), anyMap(), eq(3600000L))).willReturn("new_access_token");

        // when
        LoginResult result = reissue.execute(new ReissueCommand(refreshTokenStr));

        // then
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.accessToken()).isEqualTo("new_access_token");
        verify(jwtTokenProvider).validateToken(refreshTokenStr);
        verify(refreshTokenRepository).findById(tokenId);
    }

    @Test
    @DisplayName("리프레시 토큰이 없으면 예외 발생")
    void reissue_notFound() {
        // given
        String refreshTokenStr = "valid_token";
        Claims claims = new TestClaims(Map.of("jti", 1L, "sub", "user1"));
        given(jwtTokenProvider.getClaims(refreshTokenStr)).willReturn(claims);
        given(refreshTokenRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> reissue.execute(new ReissueCommand(refreshTokenStr)))
                .isInstanceOf(RefreshTokenNotFoundException.class);
    }
}
