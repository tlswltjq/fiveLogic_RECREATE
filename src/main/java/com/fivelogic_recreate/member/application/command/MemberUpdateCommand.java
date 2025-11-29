package com.fivelogic_recreate.member.application.command;

public record MemberUpdateCommand(
        String userId,
        String email,
        String firstname,
        String lastname,
        String nickname,
        String bio,
        String memberType
) {
}
