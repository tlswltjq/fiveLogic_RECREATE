package com.fivelogic_recreate.news.domain;

public record Author(String value) {
    public Author {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("작성자는 비워둘 수 없습니다.");
        }
    }
}
