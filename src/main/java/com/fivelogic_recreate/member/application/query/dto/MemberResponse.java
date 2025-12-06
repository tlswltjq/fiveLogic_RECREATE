package com.fivelogic_recreate.member.application.query.dto;

import com.fivelogic_recreate.member.domain.Member;


public record MemberResponse(
        String userId,
        String email,
        String name
) {
    public MemberResponse(Member member) {
        this(
                member.getUserId().userId(),
                member.getEmail().value(),
                member.getName().firstName() + " " + member.getName().lastName()
        );
    }
}
