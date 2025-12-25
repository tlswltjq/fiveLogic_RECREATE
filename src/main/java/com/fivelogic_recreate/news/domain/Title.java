package com.fivelogic_recreate.news.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Title(String value) {
    public Title {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("제목은 비워둘 수 없습니다");
        }
        if (value.length() > 255) {
            throw new IllegalArgumentException("너무 긴 제목");
        }
    }
}
