package com.fivelogic_recreate.member.application.command;

public record MemberUpdateCommand(
        String userId,
        String nickname,
        String memberType
) {
}
