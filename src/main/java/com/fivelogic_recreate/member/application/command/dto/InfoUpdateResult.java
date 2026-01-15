package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.model.Member;

public record InfoUpdateResult(
        String userId,
        String nickname,
        String bio
) {
    public static InfoUpdateResult from(Member member) {
        return new InfoUpdateResult(member.getUserId().value(), member.getNickname().value(), member.getBio().value());
    }
}
