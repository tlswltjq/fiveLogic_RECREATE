package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import org.springframework.data.domain.Page;

public record GetNewsListResponse(Page<NewsQueryResponse> newsList) {
    public static GetNewsListResponse from(Page<NewsQueryResponse> newsList) {
        return new GetNewsListResponse(newsList);
    }
}
