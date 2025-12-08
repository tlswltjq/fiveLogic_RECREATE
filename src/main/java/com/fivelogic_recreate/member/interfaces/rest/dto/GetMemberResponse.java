package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.query.dto.MemberResponse;

public record GetMemberResponse(
        String userId,
        String email,
        String memberType,
        boolean isActive
) {
    public GetMemberResponse (MemberResponse memberInfo){
        this(memberInfo.userId(), memberInfo.email(), memberInfo.memberType(), memberInfo.isActive());
    }
}
