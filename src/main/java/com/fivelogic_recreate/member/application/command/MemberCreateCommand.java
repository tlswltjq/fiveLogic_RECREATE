package com.fivelogic_recreate.member.application.command;

public record MemberCreateCommand(
        String userId,
        String password,
        String firstname,
        String lastname,
        String nickname
) {
}
