package com.fivelogic_recreate.member.interfaces.rest.dto;

public record CreateMemberResponse(
        String userId, String name, String email
) {
}
