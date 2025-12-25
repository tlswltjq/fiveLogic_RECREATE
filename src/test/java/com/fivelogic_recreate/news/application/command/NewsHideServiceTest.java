package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsHideNotAllowedException;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsHideServiceTest {
    @Mock
    private NewsRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsHideService newsHideService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 숨김 처리하고, 상태가 HIDDEN으로 변경된다.")
    void shouldHideNewsSuccessfully() {
        Long newsId = 1L;
        NewsHideCommand command = new NewsHideCommand(newsId);
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.PUBLISHED).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        NewsHideResult result = newsHideService.hideNews(command);

        verify(newsRepositoryPort).findById(new NewsId(newsId));

        assertThat(result.newsId()).isEqualTo(newsId);
        assertThat(result.status()).isEqualTo(NewsStatus.HIDDEN);
    }

    @Test
    @DisplayName("숨김 처리하려는 뉴스가 존재하지 않으면 예외를 발생시킨다.")
    void shouldThrowNotFoundExceptionWhenHiding() {
        Long newsId = 999L;
        NewsHideCommand command = new NewsHideCommand(newsId);
        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsHideService.hideNews(command))
                .isInstanceOf(NewsNotFoundException.class);
    }

    @Test
    @DisplayName("뉴스 상태 전이가 유효하지 않을 때 숨김 예외를 발생시킨다.")
    void shouldThrowNotAllowedExceptionWhenStateTransitionFailsOnHide() {
        Long newsId = 1L;
        NewsHideCommand command = new NewsHideCommand(newsId);
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.DRAFT).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        assertThatThrownBy(() -> newsHideService.hideNews(command))
                .isInstanceOf(NewsHideNotAllowedException.class);
    }
}
