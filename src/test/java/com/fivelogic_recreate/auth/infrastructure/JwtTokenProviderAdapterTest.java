package com.fivelogic_recreate.auth.infrastructure;

import com.fivelogic_recreate.auth.application.dto.AuthTokens;
import com.fivelogic_recreate.auth.domain.model.RefreshToken;
import com.fivelogic_recreate.auth.domain.port.RefreshTokenRepository;
import com.fivelogic_recreate.global.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderAdapterTest {

    @InjectMocks
    private JwtTokenProviderAdapter jwtTokenProviderAdapter;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("토큰 발급 성공: AccessToken 생성, RefreshToken 저장 및 생성")
    void issueTokens_success() {
        // given
        String userId = "user1";
        String role = "MENTEE";
        String accessToken = "access.token.jwt";
        String refreshTokenJwt = "refresh.token.jwt";

        given(refreshTokenRepository.save(any(RefreshToken.class)))
                .willAnswer(invocation -> {
                    return invocation.getArgument(0);
                });


        given(jwtTokenProvider.createToken(eq(userId), argThat(map -> map.containsKey("role")), anyLong()))
                .willReturn(accessToken);
        given(jwtTokenProvider.createToken(eq(userId), argThat(map -> map.containsKey("jti")), anyLong()))
                .willReturn(refreshTokenJwt);

        // when
        AuthTokens tokens = jwtTokenProviderAdapter.issueTokens(userId, role);

        // then
        assertThat(tokens.accessToken()).isEqualTo(accessToken);
        assertThat(tokens.refreshToken()).isEqualTo(refreshTokenJwt);

        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }
}
