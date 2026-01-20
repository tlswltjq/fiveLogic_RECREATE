package com.fivelogic_recreate.member.interfaces.rest.dto;

public record GetMemberResponse(
        Long memberId,
        String userId,
        String nickname,
        String memberType,
        String name,
        String email,
        String bio,
        boolean isActive
) {
}
