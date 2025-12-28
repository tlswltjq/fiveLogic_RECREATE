package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public record GetNewsListResponse(
        List<NewsQueryResponse> newsList,
        long totalCount,
        int pageSize,
        int pageNumber,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {
    public static GetNewsListResponse from(Page<NewsQueryResponse> page) {
        return new GetNewsListResponse(
                page.getContent(),
                page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious());
    }
}
