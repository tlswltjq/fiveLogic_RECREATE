package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsHideResult(
        Long newsId,
        String title,
        NewsStatus status
) {
}
