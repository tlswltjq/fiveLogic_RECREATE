package com.fivelogic_recreate.member.domain;

public record UserPassword(String value) {
    public UserPassword {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("잘못된 사용자 PW 입니다.");
        }
        if (value.length() < 5 || value.length() > 12) {
            throw new IllegalArgumentException("사용자 PW는 5자 이상 12자 이하이어야 합니다.");
        }
    }
}
