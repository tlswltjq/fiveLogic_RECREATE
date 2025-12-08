package com.fivelogic_recreate.member.interfaces.rest.dto;

import com.fivelogic_recreate.member.application.command.dto.MemberCreateCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMemberRequest(
        @NotBlank
        @Size(min = 5, max = 12)
        String userId,
        @NotBlank
        String password,
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
        String bio
) {
    public MemberCreateCommand toCommand() {
        return new MemberCreateCommand(userId, password, email, firstname, lastname, nickname, bio);
    }
}