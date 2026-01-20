package com.fivelogic_recreate.member.interfaces.rest.dto;

public record UpdateMemberResponse(
        String userId,
        String email,
        String nickname,
        String bio
) {
}
