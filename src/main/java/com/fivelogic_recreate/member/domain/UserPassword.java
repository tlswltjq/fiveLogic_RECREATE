package com.fivelogic_recreate.member.domain;

public record UserPassword(String password) {
    public UserPassword {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("잘못된 사용자 PW 입니다.");
        }
        if (password.length() < 5 || password.length() > 12) {
            throw new IllegalArgumentException("사용자 PW는 5자 이상 12자 이하이어야 합니다.");
        }
    }
}
