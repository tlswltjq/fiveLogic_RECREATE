package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.command.dto.MemberInfo;

public record UpdateMemberResponse(
        String userId,
        String email,
        String name,
        String nickname,
        String bio,
        String memberType
) {
    public UpdateMemberResponse(MemberInfo memberInfo) {
        this(memberInfo.userId(), memberInfo.email(), memberInfo.name(), memberInfo.nickname(), memberInfo.bio(), memberInfo.memberType());
    }
}
