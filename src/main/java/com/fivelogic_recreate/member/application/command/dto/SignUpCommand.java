package com.fivelogic_recreate.member.application.command.dto;

public record SignUpCommand(
        String userId,
        String password,
        String email,
        String firstname,
        String lastname,
        String nickname,
        String bio
) {
}
