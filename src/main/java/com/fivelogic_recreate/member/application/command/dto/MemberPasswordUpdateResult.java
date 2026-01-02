package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.model.Member;

public record MemberPasswordUpdateResult(
        Long id,
        String userId
) {
    public static MemberPasswordUpdateResult from(Member member) {
        return new MemberPasswordUpdateResult(member.getId(), member.getUserId().value());
    }
}
