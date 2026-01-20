package com.fivelogic_recreate.member.application.command.dto;

public record WithdrawCommand(
        String userId,
        String reasonWhy//탈퇴사유기록 향후 사용할 수 있지 않을까?
) {
}
