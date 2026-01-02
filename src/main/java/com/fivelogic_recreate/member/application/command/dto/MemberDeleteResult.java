package com.fivelogic_recreate.member.application.command.dto;

import com.fivelogic_recreate.member.domain.model.Member;

public record MemberDeleteResult(
        Long id,
        String userId,
        String name,
        String nickname,
        String memberType,
        Boolean isActivated
) {
    public static MemberDeleteResult from(Member member) {
        return new MemberDeleteResult(member.getId(), member.getUserId().value(), member.getName().value(),
                member.getNickname().value(), member.getMemberType().name(), member.getIsActivated());
    }
}
