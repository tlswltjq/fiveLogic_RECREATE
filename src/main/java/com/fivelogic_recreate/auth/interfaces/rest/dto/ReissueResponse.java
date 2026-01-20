package com.fivelogic_recreate.auth.interfaces.rest.dto;

public record ReissueResponse(
        String accessToken,
        String refreshToken) {
}
