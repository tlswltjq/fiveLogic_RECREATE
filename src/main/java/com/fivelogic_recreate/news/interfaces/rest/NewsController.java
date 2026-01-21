package com.fivelogic_recreate.news.interfaces.rest;

import com.fivelogic_recreate.common.rest.ApiResponse;
import com.fivelogic_recreate.news.application.command.*;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.application.query.GetNewsDetail;
import com.fivelogic_recreate.news.application.query.GetNewsList;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.interfaces.rest.dto.CreateNewsRequest;
import com.fivelogic_recreate.news.interfaces.rest.dto.CreateNewsResponse;
import com.fivelogic_recreate.news.interfaces.rest.dto.GetNewsListResponse;
import com.fivelogic_recreate.news.interfaces.rest.dto.GetNewsResponse;
import com.fivelogic_recreate.news.interfaces.rest.dto.UpdateNewsRequest;
import com.fivelogic_recreate.news.interfaces.rest.dto.UpdateNewsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final RegisterNews registerNews;
    private final EditNews editNews;
    private final PublishNews publishNews;
    private final HideNews hideNews;
    private final UnhideNews unhideNews;
    private final RemoveNews removeNews;

    private final GetNewsDetail getNewsDetail;
    private final GetNewsList getNewsList;

    @PostMapping
    public ApiResponse<CreateNewsResponse> createNews(@AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody CreateNewsRequest request) {
        NewsCreateResult result = registerNews.createNews(
                new NewsCreateCommand(
                        request.title(),
                        request.description(),
                        request.textContent(),
                        request.videoUrl(),
                        user.getUsername()));
        return ApiResponse.success(201, "뉴스 생성 완료", new CreateNewsResponse(result));
    }

    @GetMapping("/{newsId}")
    public ApiResponse<GetNewsResponse> getNews(@PathVariable Long newsId) {
        NewsQueryResponse result = getNewsDetail.findById(newsId);
        return ApiResponse.success(200, "뉴스 조회 완료", new GetNewsResponse(result));
    }

    @GetMapping
    public ApiResponse<GetNewsListResponse> getNewsList(Pageable pageable) {
        Page<NewsQueryResponse> result = getNewsList.findByPublishedDateBefore(LocalDateTime.now(), pageable);
        return ApiResponse.success(200, "뉴스 목록 조회 완료", GetNewsListResponse.from(result));
    }

    @PutMapping("/{newsId}")
    public ApiResponse<UpdateNewsResponse> updateNews(@PathVariable Long newsId,
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody UpdateNewsRequest request) {
        NewsUpdateResult result = editNews.updateNews(
                new NewsUpdateCommand(
                        newsId,
                        request.title(),
                        request.description(),
                        request.textContent(),
                        request.videoUrl(),
                        user.getUsername()));
        return ApiResponse.success(200, "뉴스 수정 완료", new UpdateNewsResponse(result));
    }

    @PostMapping("/{newsId}/publish")
    public ApiResponse<Void> publishNews(@PathVariable Long newsId,
            @AuthenticationPrincipal UserDetails user) {
        publishNews.publishNews(new NewsPublishCommand(newsId, user.getUsername()));
        return ApiResponse.success(200, "뉴스 발행 완료", null);
    }

    @PostMapping("/{newsId}/hide")
    public ApiResponse<Void> hideNews(@PathVariable Long newsId,
            @AuthenticationPrincipal UserDetails user) {
        hideNews.hideNews(new NewsHideCommand(newsId, user.getUsername()));
        return ApiResponse.success(200, "뉴스 숨김 완료", null);
    }

    @PostMapping("/{newsId}/unhide")
    public ApiResponse<Void> unhideNews(@PathVariable Long newsId,
            @AuthenticationPrincipal UserDetails user) {
        unhideNews.unHideNews(new NewsUnHideCommand(newsId, user.getUsername()));
        return ApiResponse.success(200, "뉴스 숨김 해제 완료", null);
    }

    @DeleteMapping("/{newsId}")
    public ApiResponse<Void> deleteNews(@PathVariable Long newsId,
            @AuthenticationPrincipal UserDetails user) {
        removeNews.deleteNews(new NewsDeleteCommand(newsId, user.getUsername()));
        return ApiResponse.success(200, "뉴스 삭제 완료", null);
    }
}
