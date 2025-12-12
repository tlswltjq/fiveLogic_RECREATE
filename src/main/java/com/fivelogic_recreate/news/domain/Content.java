package com.fivelogic_recreate.news.domain;

public record Content(
        String text,
        String videoUrl
) {
    public Content {
        if(text == null || text.isEmpty()) {
            throw new IllegalArgumentException("본문은 비울 수 없습니다.");
        }
        if(videoUrl == null || videoUrl.isEmpty()) {
            throw new IllegalArgumentException("영상 링크는 비울 수 없습니다.");
        }
    }
}
