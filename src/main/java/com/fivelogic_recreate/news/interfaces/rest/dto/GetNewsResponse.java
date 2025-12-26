package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;

public record GetNewsResponse(NewsQueryResponse news) {
}
