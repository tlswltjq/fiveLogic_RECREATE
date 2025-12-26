package com.fivelogic_recreate.news.application;

import com.fivelogic_recreate.news.application.command.*;
import com.fivelogic_recreate.news.application.command.dto.*;
import com.fivelogic_recreate.news.application.query.NewsQueryService;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsCreateService newsCreateService;
    private final NewsDeleteService newsDeleteService;
    private final NewsHideService newsHideService;
    private final NewsPublishService newsPublishService;
    private final NewsUnHideService newsUnHideService;
    private final NewsUpdateService newsUpdateService;
    private final NewsQueryService newsQueryService;

    public NewsCreateResult createNews(NewsCreateCommand command) {
        return newsCreateService.createNews(command);
    }

    public NewsDeleteResult deleteNews(NewsDeleteCommand command) {
        return newsDeleteService.deleteNews(command);
    }

    public NewsHideResult hideNews(NewsHideCommand command) {
        return newsHideService.hideNews(command);
    }

    public NewsPublishResult publishNews(NewsPublishCommand command) {
        return newsPublishService.publishNews(command);
    }

    public NewsHideResult unhideNews(NewsUnHideCommand command) {
        return newsUnHideService.unHideNews(command);
    }

    public NewsUpdateResult updateNews(NewsUpdateCommand command) {
        return newsUpdateService.updateNews(command);
    }

    public NewsQueryResponse getNews(Long newsId) {
        return newsQueryService.findById(newsId);
    }

    public Page<NewsQueryResponse> getNewsList(Pageable pageable) {
        return newsQueryService.findByPublishedDateBefore(LocalDateTime.now(), pageable);
    }

    public Page<NewsQueryResponse> getNewsListByAuthor(String authorId, Pageable pageable) {
        return newsQueryService.findByAuthorId(authorId, pageable);
    }
}
