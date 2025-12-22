package com.fivelogic_recreate.member.interfaces.rest.dto;

public record UpdateMemberResponse(
        String userId,
        String email,
        String name,
        String nickname,
        String bio,
        String memberType
) {
}
