package com.fivelogic_recreate.auth.application.dto;

public record LoginCommand(
        String userId,
        String password
) {
}
