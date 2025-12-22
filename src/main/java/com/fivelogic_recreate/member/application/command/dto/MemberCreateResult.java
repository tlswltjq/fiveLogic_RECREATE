package com.fivelogic_recreate.member.application.command.dto;

public record MemberCreateResult(
        Long id,
        String userId,
        String name,
        String nickname,
        String memberType,
        Boolean isActivated,
        String email,
        String bio
) {
}
