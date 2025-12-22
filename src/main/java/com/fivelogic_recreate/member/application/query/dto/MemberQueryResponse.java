package com.fivelogic_recreate.member.application.query.dto;

public record MemberQueryResponse(
        String userId,
        String email,
        String name,
        String nickname,
        String memberType,
        String bio,
        boolean isActive

) {
}
