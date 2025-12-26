package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;

public record UpdateNewsResponse(
        Long id,
        String title,
        String description,
        String textContent,
        String videoUrl,
        String status) {
    public UpdateNewsResponse(NewsUpdateResult result) {
        this(result.newsId(), result.title(), result.description(), result.textContent(), result.videoUrl(),
                result.status().name());
    }
}
