package com.fivelogic_recreate.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private static final String SECRET_KEY = "test_secret_key_must_be_at_least_256_bits_long_for_security";
    private static final long VALIDITY_MS = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(SECRET_KEY);
    }

    @Test
    @DisplayName("JWT 토큰 생성 성공")
    void createToken_success() {
        // given
        String subject = "user1";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "MENTEE");

        // when
        String token = jwtTokenProvider.createToken(subject, claims, VALIDITY_MS);

        // then
        assertThat(token).isNotNull();
        assertThat(token.split("\\.")).hasSize(3); // Header.Payload.Signature
    }

    @Test
    @DisplayName("토큰에서 Subject 및 Claim 추출 성공")
    void getClaims_success() {
        // given
        String subject = "user1";
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("role", "MENTEE");
        String token = jwtTokenProvider.createToken(subject, claimsMap, VALIDITY_MS);

        // when
        Claims claims = jwtTokenProvider.getClaims(token);
        String extractedSubject = jwtTokenProvider.getSubject(token);

        // then
        assertThat(extractedSubject).isEqualTo(subject);
        assertThat(claims.get("role")).isEqualTo("MENTEE");
    }

    @Test
    @DisplayName("유효한 토큰 검증 성공")
    void validateToken_success() {
        // given
        String token = jwtTokenProvider.createToken("user1", new HashMap<>(), VALIDITY_MS);

        // when & then
        // 예외가 발생하지 않음 = 성공
        jwtTokenProvider.validateToken(token);
    }

    @Test
    @DisplayName("만료된 토큰 검증 시 예외 발생")
    void validateToken_fail_expired() {
        // given
        // 유효기간 0ms -> 생성 즉시 만료
        String expiredToken = jwtTokenProvider.createToken("user1", new HashMap<>(), 0);

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(expiredToken))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    @DisplayName("서명이 잘못된 토큰 검증 시 예외 발생")
    void validateToken_fail_signature() {
        // given
        String validToken = jwtTokenProvider.createToken("user1", new HashMap<>(), VALIDITY_MS);
        String invalidToken = validToken + "invalid"; // 서명 조작

        // when & then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(invalidToken))
                .isInstanceOf(SignatureException.class);
    }
}
