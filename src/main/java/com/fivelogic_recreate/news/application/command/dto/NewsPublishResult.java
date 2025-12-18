package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

import java.time.LocalDateTime;

public record NewsPublishResult(
        Long newsId,
        String title,
        LocalDateTime publishedDate,
        NewsStatus status
) {
}
