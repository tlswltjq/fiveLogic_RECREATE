package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.command.dto.MemberUpdateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String firstname,
        @NotBlank
        String lastname,
        @NotBlank
        String nickname,
        @NotNull
        String bio,
        @NotBlank
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
