package com.fivelogic_recreate.member.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserPassword(String value) {
    public UserPassword {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어 있을 수 없습니다.");
        }
    }

    public void match(UserPassword rawPassword) {
        if (!this.equals(rawPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
