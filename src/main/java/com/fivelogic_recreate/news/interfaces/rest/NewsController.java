package com.fivelogic_recreate.news.interfaces.rest;

import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.news.application.NewsService;
import com.fivelogic_recreate.news.application.command.dto.*;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.interfaces.rest.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ApiResponse<CreateNewsResponse> createNews(@Valid @RequestBody CreateNewsRequest request) {
        NewsCreateResult result = newsService.createNews(request.toCommand());
        return ApiResponse.success(201, "뉴스 생성 완료", new CreateNewsResponse(result));
    }

    @GetMapping("/{newsId}")
    public ApiResponse<GetNewsResponse> getNews(@PathVariable Long newsId) {
        NewsQueryResponse result = newsService.getNews(newsId);
        return ApiResponse.success(200, "뉴스 조회 완료", new GetNewsResponse(result));
    }

    @GetMapping
    public ApiResponse<GetNewsListResponse> getNewsList(Pageable pageable) {
        Page<NewsQueryResponse> result = newsService.getNewsList(pageable);
        return ApiResponse.success(200, "뉴스 목록 조회 완료", GetNewsListResponse.from(result));
    }

    @PutMapping("/{newsId}")
    public ApiResponse<UpdateNewsResponse> updateNews(@PathVariable Long newsId,
            @Valid @RequestBody UpdateNewsRequest request) {
        NewsUpdateResult result = newsService.updateNews(request.toCommand(newsId));
        return ApiResponse.success(200, "뉴스 수정 완료", new UpdateNewsResponse(result));
    }

    @PostMapping("/{newsId}/publish")
    public ApiResponse<Void> publishNews(@PathVariable Long newsId, @RequestBody PublishNewsRequest request) {
        newsService.publishNews(request.toCommand(newsId));
        return ApiResponse.success(200, "뉴스 발행 완료", null);
    }

    @PostMapping("/{newsId}/hide")
    public ApiResponse<Void> hideNews(@PathVariable Long newsId, @RequestBody HideNewsRequest request) {
        newsService.hideNews(request.toCommand(newsId));
        return ApiResponse.success(200, "뉴스 숨김 완료", null);
    }

    @PostMapping("/{newsId}/unhide")
    public ApiResponse<Void> unhideNews(@PathVariable Long newsId, @RequestBody UnhideNewsRequest request) {
        newsService.unhideNews(request.toCommand(newsId));
        return ApiResponse.success(200, "뉴스 숨김 해제 완료", null);
    }

    @DeleteMapping("/{newsId}")
    public ApiResponse<Void> deleteNews(@PathVariable Long newsId) {
        newsService.deleteNews(new NewsDeleteCommand(newsId));
        return ApiResponse.success(200, "뉴스 삭제 완료", null);
    }
}
