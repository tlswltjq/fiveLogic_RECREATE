package com.fivelogic_recreate.member.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserPassword(String value) {
    public UserPassword {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어 있을 수 없습니다.");
        }
        if (value.length() < 5 || value.length() > 12) {
            throw new IllegalArgumentException("사용자 PW는 5자 이상 12자 이하이어야 합니다.");
        }
    }

    public boolean match(String rawPassword) {
        return this.value.equals(rawPassword);
    }
}
