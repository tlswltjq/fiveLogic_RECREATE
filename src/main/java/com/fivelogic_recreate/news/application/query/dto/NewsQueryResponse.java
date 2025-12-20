package com.fivelogic_recreate.news.application.query.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

import java.time.LocalDateTime;

public record NewsQueryResponse(
        String title,
        String description,
        String content,
        String videoUrl,
        String authorId,
        LocalDateTime publishedDate,
        NewsStatus status
) {
}
