package com.fivelogic_recreate.member.application.command.dto;

public record EmailUpdateCommand(
        String userId,
        String email
) {
}
