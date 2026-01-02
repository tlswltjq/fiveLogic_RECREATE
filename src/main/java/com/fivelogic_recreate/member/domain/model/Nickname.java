package com.fivelogic_recreate.member.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Nickname(String value) {
    public Nickname {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("닉네임은 비어 있을 수 없습니다.");
        }
    }
}
