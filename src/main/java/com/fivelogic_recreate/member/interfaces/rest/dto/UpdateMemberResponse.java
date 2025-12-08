package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.domain.Member;

public record UpdateMemberResponse(
        String userId,
        String email,
        String name,
        String nickname,
        String bio,
        String memberType
) {
    public UpdateMemberResponse(Member member) {
        this(member.getUserId().value(), member.getEmail().value(), member.getName().value(), member.getNickname().value(), member.getBio().value(), member.getMemberType().name());
    }
}
