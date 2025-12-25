package com.fivelogic_recreate.news.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record VideoUrl(
        String value
) {
    public VideoUrl {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("영상 링크는 비울 수 없습니다.");
        }
    }
}
