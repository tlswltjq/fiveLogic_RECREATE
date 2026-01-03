package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsHideResult(
        Long newsId,
        String title,
        NewsStatus status
) {
    public static NewsHideResult from(News news) {
        return new NewsHideResult(news.getId(), news.getTitle().value(), news.getStatus());
    }
}
