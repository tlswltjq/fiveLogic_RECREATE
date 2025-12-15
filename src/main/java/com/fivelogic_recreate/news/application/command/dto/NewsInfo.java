package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.News;

import java.time.LocalDateTime;

public record NewsInfo(
        Long id,
        String title,
        String description,
        String textContent,
        String videoUrl,
        String authorId,
        LocalDateTime publishedDate,
        String status
) {
    public NewsInfo(News news) {
        this(news.getId().value(), news.getTitle().value(), news.getDescription().value(), news.getContent().text().value(), news.getContent().videoUrl().value(), news.getAuthorId().value(), news.getPublishedDate(), news.getStatus().name());
    }
}
