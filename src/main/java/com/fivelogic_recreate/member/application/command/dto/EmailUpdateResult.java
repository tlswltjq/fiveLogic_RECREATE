package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.model.Member;

public record EmailUpdateResult(
        String userId,
        String email
) {
    public static EmailUpdateResult from(Member member) {
        return new EmailUpdateResult(member.getUserId().value(), member.getEmail().value());
    }
}
