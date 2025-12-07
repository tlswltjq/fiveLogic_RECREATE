package com.fivelogic_recreate.member.domain;

public record Nickname(String value) {
    public Nickname {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("잘못된 ID 입니다.");
        }
    }
}
