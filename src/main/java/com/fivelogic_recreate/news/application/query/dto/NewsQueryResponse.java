package com.fivelogic_recreate.news.application.query.dto;

import com.fivelogic_recreate.news.domain.NewsStatus;

import java.time.LocalDateTime;

public interface NewsQueryResponse {
    String getTitle();
    String getDescription();
    String getContent();
    String getVideoUrl();
    String getAuthorId();
    LocalDateTime getPublishedDate();
    NewsStatus getStatus();
}
