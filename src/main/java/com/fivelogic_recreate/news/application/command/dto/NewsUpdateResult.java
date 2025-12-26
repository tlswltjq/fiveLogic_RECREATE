package com.fivelogic_recreate.news.application.command.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

public record NewsUpdateResult(
        Long newsId,
        String title,
        String description,
        String textContent,
        String videoUrl,
        NewsStatus status) {
}
