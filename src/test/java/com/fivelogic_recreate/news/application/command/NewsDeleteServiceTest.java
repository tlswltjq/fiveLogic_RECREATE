package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsDeleteNotAllowedException;
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
class NewsDeleteServiceTest {
    @Mock
    private NewsRepositoryPort newsRepositoryPort;

    @InjectMocks
    private NewsDeleteService newsDeleteService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 삭제하고, 상태가 DELETED로 변경된다.")
    void shouldDeleteNewsSuccessfully() {
        Long newsId = 1L;
        Member author = new MemberFixture().withUserId("author-1").build();
        NewsDeleteCommand command = new NewsDeleteCommand(newsId, "author-1");
        News news = newsFixture.withId(newsId).withStatus(NewsStatus.PUBLISHED).withAuthor(author).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        NewsDeleteResult result = newsDeleteService.deleteNews(command);

        verify(newsRepositoryPort).findById(new NewsId(newsId));

        assertThat(result.newsId()).isEqualTo(newsId);
        assertThat(result.status()).isEqualTo(NewsStatus.DELETED);
    }

    @Test
    @DisplayName("삭제하려는 뉴스가 존재하지 않으면 예외를 발생시킨다.")
    void shouldThrowNotFoundExceptionWhenDeleting() {
        Long newsId = 999L;
        NewsDeleteCommand command = new NewsDeleteCommand(newsId, "any-user");
        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsDeleteService.deleteNews(command))
                .isInstanceOf(NewsNotFoundException.class);
    }

    @Test
    @DisplayName("뉴스 상태 전이가 유효하지 않을 때 삭제 예외를 발생시킨다.")
    void shouldThrowNotAllowedExceptionWhenStateTransitionFails() {
        Long newsId = 1L;
        Member author = new MemberFixture().withUserId("author-1").build();
        NewsDeleteCommand command = new NewsDeleteCommand(newsId, "author-1");

        News news = newsFixture.withId(newsId).withStatus(NewsStatus.DELETED).withAuthor(author).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        // when & then
        assertThatThrownBy(() -> newsDeleteService.deleteNews(command))
                .isInstanceOf(NewsDeleteNotAllowedException.class);
    }
}
