package com.fivelogic_recreate.member.application.command.dto;

public record PasswordUpdateCommand(
        String userId,
        String password
) {
}
