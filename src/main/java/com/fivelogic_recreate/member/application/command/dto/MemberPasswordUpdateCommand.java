package com.fivelogic_recreate.member.application.command.dto;

public record MemberPasswordUpdateCommand(
        String userId,
        String password
) {
}
