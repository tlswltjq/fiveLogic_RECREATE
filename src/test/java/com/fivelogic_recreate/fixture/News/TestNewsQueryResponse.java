package com.fivelogic_recreate.fixture.News;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.NewsStatus;

import java.time.LocalDateTime;

public record TestNewsQueryResponse(
        String title,
        String description,
        String content,
        String videoUrl,
        String authorId,
        LocalDateTime publishedDate,
        NewsStatus status) implements NewsQueryResponse {
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    @Override
    public NewsStatus getStatus() {
        return status;
    }
}
