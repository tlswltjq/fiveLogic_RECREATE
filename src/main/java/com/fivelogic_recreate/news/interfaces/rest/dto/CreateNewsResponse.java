package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;

public record CreateNewsResponse(
        Long id,
        String title,
        String authorId,
        String status) {
    public CreateNewsResponse(NewsCreateResult result) {
        this(result.newsId(), result.title(), result.authorId(), result.status().name());
    }
}
