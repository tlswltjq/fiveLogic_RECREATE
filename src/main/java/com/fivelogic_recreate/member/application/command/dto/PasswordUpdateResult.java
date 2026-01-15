package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.model.Member;

public record PasswordUpdateResult(
        Long id,
        String userId
) {
    public static PasswordUpdateResult from(Member member) {
        return new PasswordUpdateResult(member.getId(), member.getUserId().value());
    }
}
