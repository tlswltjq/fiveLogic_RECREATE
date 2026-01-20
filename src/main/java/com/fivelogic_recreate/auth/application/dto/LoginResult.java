package com.fivelogic_recreate.auth.application.dto;

public record LoginResult(
        String username,
        String nickname,
        String memberType
) {
}
