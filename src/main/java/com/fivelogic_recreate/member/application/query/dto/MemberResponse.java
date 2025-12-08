package com.fivelogic_recreate.member.application.query.dto;

import com.fivelogic_recreate.member.domain.Member;


public record MemberResponse(
        String userId,
        String email,
        String name,
        String nickname,
        String memberType,
        String bio,
        boolean isActive

) {
    public MemberResponse(Member member) {
        this(
                member.getUserId().value(),
                member.getEmail().value(),
                member.getName().firstName() + " " + member.getName().lastName(),
                member.getNickname().value(),
                member.getMemberType().name(),
                member.getBio().value(),
                member.getIsActivated()
        );
    }
}
