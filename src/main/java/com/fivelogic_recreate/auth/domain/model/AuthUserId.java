package com.fivelogic_recreate.auth.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record AuthUserId(String value) {
    public AuthUserId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 ID는 비어 있을 수 없습니다.");
        }
    }
}
