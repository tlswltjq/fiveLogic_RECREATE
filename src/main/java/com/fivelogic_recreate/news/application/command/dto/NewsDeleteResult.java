package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsDeleteResult(
        Long newsId,
        String title,
        NewsStatus status
) {
}
