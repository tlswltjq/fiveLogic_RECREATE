package com.fivelogic_recreate.auth.interfaces.rest.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken) {
}
