package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import com.fivelogic_recreate.news.exception.NewsUnHideNotAllowedException;
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
class NewsUnHideServiceTest {
    @Mock
    private NewsRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsUnHideService newsUnHideService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스 숨김을 성공적으로 해제하고, 상태가 PUBLISHED로 변경된다.")
    void shouldUnHideNewsSuccessfully() {
        Long newsId = 1L;
        NewsUnHideCommand command = new NewsUnHideCommand(newsId);
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.HIDDEN).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));
        when(newsRepositoryPort.save(any(News.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NewsHideResult result = newsUnHideService.unHideNews(command);

        verify(newsRepositoryPort).findById(new NewsId(newsId));
        verify(newsRepositoryPort).save(any(News.class));
        assertThat(result.status()).isEqualTo(NewsStatus.PUBLISHED);
    }

    @Test
    @DisplayName("숨김 해제하려는 뉴스가 존재하지 않으면 예외를 발생시킨다.")
    void shouldThrowNotFoundExceptionWhenUnHiding() {
        Long newsId = 999L;
        NewsUnHideCommand command = new NewsUnHideCommand(newsId);
        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsUnHideService.unHideNews(command))
                .isInstanceOf(NewsNotFoundException.class);
    }

    @Test
    @DisplayName("뉴스 상태 전이가 유효하지 않을 때 숨김 해제 예외를 발생시킨다.")
    void shouldThrowNotAllowedExceptionWhenStateTransitionFailsOnUnHide() {
        Long newsId = 1L;
        NewsUnHideCommand command = new NewsUnHideCommand(newsId);
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.DRAFT).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        assertThatThrownBy(() -> newsUnHideService.unHideNews(command))
                .isInstanceOf(NewsUnHideNotAllowedException.class);
    }
}
