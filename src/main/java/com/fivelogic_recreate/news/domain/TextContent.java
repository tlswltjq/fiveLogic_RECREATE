package com.fivelogic_recreate.news.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record TextContent(
        String value) {
    public TextContent {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("본문은 비울 수 없습니다.");
        }
    }
}
