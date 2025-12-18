package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsCreateResult(
        Long newsId,
        String title,
        String authorId,
        NewsStatus status
) {
}
