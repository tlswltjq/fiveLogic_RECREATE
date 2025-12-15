package com.fivelogic_recreate.news.domain;

public record Content(
        TextContent text,
        VideoUrl videoUrl
) {
    public Content(String text, String videoUrl) {
        this(new TextContent(text), new VideoUrl(videoUrl));
    }
}
