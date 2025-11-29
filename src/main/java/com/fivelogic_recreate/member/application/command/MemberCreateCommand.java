package com.fivelogic_recreate.member.application.command;

public record MemberCreateCommand(
        String userId,
        String password,
        String email,
        String firstname,
        String lastname,
        String nickname,
        String bio
) {
}
