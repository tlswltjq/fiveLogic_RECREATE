package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.model.Member;

public record SignUpResult(
        String userId,
        String name,
        String nickname,
        String memberType,
        Boolean isActivated,
        String email,
        String bio
) {
    public static SignUpResult from(Member member) {
        return new SignUpResult(member.getUserId().value(), member.getName().value(), member.getNickname().value(), member.getMemberType().name(), member.getIsActivated(), member.getEmail().value(), member.getBio().value());
    }
}
