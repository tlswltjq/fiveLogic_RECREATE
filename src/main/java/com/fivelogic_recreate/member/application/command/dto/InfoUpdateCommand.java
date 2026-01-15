package com.fivelogic_recreate.member.application.command.dto;

public record InfoUpdateCommand(
        String userId,
        String nickname,
        String bio
) {
}
