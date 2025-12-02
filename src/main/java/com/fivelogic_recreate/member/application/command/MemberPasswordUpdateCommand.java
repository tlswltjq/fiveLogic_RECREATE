package com.fivelogic_recreate.member.application.command;

public record MemberPasswordUpdateCommand(
        String userId,
        String password
) {
}
