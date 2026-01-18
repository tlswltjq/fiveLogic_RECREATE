package com.fivelogic_recreate.member.application.query.dto;

public record MemberDetail(
        Long memberId,
        String userId,
        String nickname,
        String memberType,
        String name,
        String email,
        String bio
) {
}
