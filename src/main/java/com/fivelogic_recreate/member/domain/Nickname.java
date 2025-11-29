package com.fivelogic_recreate.member.domain;

public record Nickname(String nickname) {
    public Nickname {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }
}
