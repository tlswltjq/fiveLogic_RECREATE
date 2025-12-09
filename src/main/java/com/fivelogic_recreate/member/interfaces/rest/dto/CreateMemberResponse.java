package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.command.dto.MemberInfo;

public record CreateMemberResponse(
        String userId, String name, String email
) {
    public CreateMemberResponse(MemberInfo memberinfo) {
        this(memberinfo.userId(), memberinfo.name(), memberinfo.email());
    }
}
