package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import com.fivelogic_recreate.news.exception.NewsPublishNotAllowedException;
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
class NewsPublishServiceTest {

    @Mock
    private NewsRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsPublishService newsPublishService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 발행하고, 상태가 PUBLISHED로 변경된다.")
    void shouldPublishNewsSuccessfully() {
        Long newsId = 1L;
        NewsPublishCommand command = new NewsPublishCommand(newsId);
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.READY).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));
        when(newsRepositoryPort.save(any(News.class))).thenAnswer(invocation -> invocation.getArgument(0));

        NewsPublishResult result = newsPublishService.publishNews(command);

        verify(newsRepositoryPort).findById(new NewsId(newsId));
        verify(newsRepositoryPort).save(any(News.class));
        assertThat(result.status()).isEqualTo(NewsStatus.PUBLISHED);
    }

    @Test
    @DisplayName("발행하려는 뉴스가 존재하지 않으면 예외를 발생시킨다.")
    void shouldThrowNotFoundExceptionWhenPublishing() {
        Long newsId = 999L;
        NewsPublishCommand command = new NewsPublishCommand(newsId);
        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsPublishService.publishNews(command))
                .isInstanceOf(NewsNotFoundException.class);
    }

    @Test
    @DisplayName("뉴스 상태 전이가 유효하지 않을 때 발행 예외를 발생시킨다.")
    void shouldThrowNotAllowedExceptionWhenStateTransitionFailsOnPublish() {
        Long newsId = 1L;
        NewsPublishCommand command = new NewsPublishCommand(newsId);
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.DRAFT).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        assertThatThrownBy(() -> newsPublishService.publishNews(command))
                .isInstanceOf(NewsPublishNotAllowedException.class);
    }
}
