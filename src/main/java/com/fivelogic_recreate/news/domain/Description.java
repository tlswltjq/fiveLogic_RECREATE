package com.fivelogic_recreate.news.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Description(String value) {
    public Description {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("잘못된 뉴스 설명입니다.");
        }
    }
}
