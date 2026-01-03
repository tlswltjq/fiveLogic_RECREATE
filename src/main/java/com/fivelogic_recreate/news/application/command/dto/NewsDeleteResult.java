package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsDeleteResult(
        Long newsId,
        String title,
        NewsStatus status
) {
    public static NewsDeleteResult from(News news) {
        return new NewsDeleteResult(news.getId(), news.getTitle().value(), news.getStatus());
    }
}
