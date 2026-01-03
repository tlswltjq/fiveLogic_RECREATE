package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsUpdateResult(
        Long newsId,
        String title,
        String description,
        String textContent,
        String videoUrl,
        NewsStatus status
) {
    public static NewsUpdateResult from(News news) {
        return new NewsUpdateResult(
                news.getId(),
                news.getTitle().value(),
                news.getDescription().value(),
                news.getTextContent().value(),
                news.getVideoUrl().value(),
                news.getStatus()
        );
    }
}
