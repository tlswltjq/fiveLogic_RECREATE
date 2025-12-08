package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.domain.Member;

public record CreateMemberResponse(
        String userId, String name, String email
) {
    public CreateMemberResponse(Member member) {
        this(member.getUserId().value(), member.getName().value(), member.getEmail().value());
    }
}
