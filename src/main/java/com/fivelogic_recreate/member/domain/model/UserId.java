package com.fivelogic_recreate.member.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(String value) {
    public UserId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 ID는 비어 있을 수 없습니다.");
        }
    }
}
