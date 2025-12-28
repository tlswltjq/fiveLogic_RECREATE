package com.fivelogic_recreate.news.application;

import com.fivelogic_recreate.news.application.command.NewsCreateService;
import com.fivelogic_recreate.news.application.command.NewsDeleteService;
import com.fivelogic_recreate.news.application.command.NewsHideService;
import com.fivelogic_recreate.news.application.command.NewsPublishService;
import com.fivelogic_recreate.news.application.command.NewsUnHideService;
import com.fivelogic_recreate.news.application.command.NewsUpdateService;
import com.fivelogic_recreate.news.application.command.dto.*;
import com.fivelogic_recreate.news.application.query.NewsQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsCreateService newsCreateService;
    @Mock
    private NewsDeleteService newsDeleteService;
    @Mock
    private NewsHideService newsHideService;
    @Mock
    private NewsPublishService newsPublishService;
    @Mock
    private NewsUnHideService newsUnHideService;
    @Mock
    private NewsUpdateService newsUpdateService;
    @Mock
    private NewsQueryService newsQueryService;

    @InjectMocks
    private NewsService newsService;

    @Test
    @DisplayName("createNews 호출 시 NewsCreateService로 위임한다.")
    void shouldDelegateCreateNews() {
        newsService.createNews(new NewsCreateCommand("Title", "Desc", "Content", "VideoUrl", "AuthorId"));
        verify(newsCreateService).createNews(any(NewsCreateCommand.class));
    }

    @Test
    @DisplayName("updateNews 호출 시 NewsUpdateService로 위임한다.")
    void shouldDelegateUpdateNews() {
        newsService.updateNews(new NewsUpdateCommand(1L, "Title", "Desc", "Content", "VideoUrl", "test-user-id"));
        verify(newsUpdateService).updateNews(any(NewsUpdateCommand.class));
    }

    @Test
    @DisplayName("getNews 호출 시 NewsQueryService로 위임한다.")
    void shouldDelegateGetNews() {
        newsService.getNews(1L);
        verify(newsQueryService).findById(1L);
    }

    @Test
    @DisplayName("deleteNews 호출 시 NewsDeleteService로 위임한다.")
    void shouldDelegateDeleteNews() {
        newsService.deleteNews(new NewsDeleteCommand(1L, "test-user-id"));
        verify(newsDeleteService).deleteNews(any(NewsDeleteCommand.class));
    }

    @Test
    @DisplayName("hideNews 호출 시 NewsHideService로 위임한다.")
    void shouldDelegateHideNews() {
        newsService.hideNews(new NewsHideCommand(1L));
        verify(newsHideService).hideNews(any(NewsHideCommand.class));
    }

    @Test
    @DisplayName("publishNews 호출 시 NewsPublishService로 위임한다.")
    void shouldDelegatePublishNews() {
        newsService.publishNews(new NewsPublishCommand(1L));
        verify(newsPublishService).publishNews(any(NewsPublishCommand.class));
    }

    @Test
    @DisplayName("unhideNews 호출 시 NewsUnHideService로 위임한다.")
    void shouldDelegateUnhideNews() {
        newsService.unhideNews(new NewsUnHideCommand(1L));
        verify(newsUnHideService).unHideNews(any(NewsUnHideCommand.class));
    }

    @Test
    @DisplayName("getNewsList 호출 시 NewsQueryService로 위임한다.")
    void shouldDelegateGetNewsList() {
        Pageable pageable = Pageable.unpaged();
        newsService.getNewsList(pageable);
        verify(newsQueryService).findByPublishedDateBefore(any(LocalDateTime.class), any(Pageable.class));
    }

    @Test
    @DisplayName("getNewsListByAuthor 호출 시 NewsQueryService로 위임한다.")
    void shouldDelegateGetNewsListByAuthor() {
        Pageable pageable = Pageable.unpaged();
        String authorId = "authorId";
        newsService.getNewsListByAuthor(authorId, pageable);
        verify(newsQueryService).findByAuthorId(authorId, pageable);
    }
}
