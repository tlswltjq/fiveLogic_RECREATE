package com.fivelogic_recreate.member.application.command.dto;

public record MemberDeleteResult(
        Long id,
        String userId,
        String name,
        String nickname,
        String memberType,
        Boolean isActivated
) {
}
