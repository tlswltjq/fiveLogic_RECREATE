package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsCreateResult(
        Long newsId,
        String title,
        String authorId,
        NewsStatus status) {
    public static NewsCreateResult from(News news) {
        return new NewsCreateResult(news.getId(), news.getTitle().value(), news.getWriterId().value(),
                news.getStatus());
    }
}
