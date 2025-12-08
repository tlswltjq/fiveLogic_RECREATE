package com.fivelogic_recreate.member.application.command.dto;

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
