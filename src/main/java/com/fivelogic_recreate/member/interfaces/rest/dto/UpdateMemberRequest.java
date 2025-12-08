package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.command.MemberUpdateCommand;

public record UpdateMemberRequest(
        //TODO Validation추가
        String email,
        String firstname,
        String lastname,
        String nickname,
        String bio,
        String memberType
) {
    public MemberUpdateCommand toCommand(String userId) {
        return new MemberUpdateCommand(userId, email,
                firstname,
                lastname,
                nickname,
                bio,
                memberType);
    }
}
