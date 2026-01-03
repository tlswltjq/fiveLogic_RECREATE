package com.fivelogic_recreate.news.domain.service;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.domain.service.dto.NewsUpdateInfo;
import com.fivelogic_recreate.news.exception.NewsAccessDeniedException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsDomainServiceTest {
    @Mock
    NewsRepositoryPort newsRepositoryPort;

    @InjectMocks
    NewsDomainService newsDomainService;

    private NewsFixture newsFixture = new NewsFixture();
    private MemberFixture memberFixture = new MemberFixture();

    @Test
    @DisplayName("뉴스 생성 성공")
    void shouldCreateNewsSuccessfully() {
        News news = newsFixture.build();
        when(newsRepositoryPort.save(any(News.class))).thenReturn(news);

        News createdNews = newsDomainService.create(news);

        assertThat(createdNews).isNotNull();
        verify(newsRepositoryPort).save(news);
    }

    @Test
    @DisplayName("뉴스 수정 성공")
    void shouldUpdateNewsSuccessfully() {
        String authorId = "author1";
        com.fivelogic_recreate.member.domain.model.Member author = memberFixture.withUserId(authorId).build();
        News news = newsFixture.withAuthor(author).build();
        NewsUpdateInfo info = new NewsUpdateInfo("newTitle", "newDesc", "newContent", "newUrl");

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        News updatedNews = newsDomainService.update(1L, authorId, info);

        assertThat(updatedNews.getTitle().value()).isEqualTo("newTitle");
        assertThat(updatedNews.getDescription().value()).isEqualTo("newDesc");
    }

    @Test
    @DisplayName("작성자가 아닌 사용자가 뉴스 수정 시 예외 발생")
    void shouldThrowExceptionWhenNonAuthorUpdatesNews() {
        String authorId = "author1";
        com.fivelogic_recreate.member.domain.model.Member author = memberFixture.withUserId(authorId).build();
        News news = newsFixture.withAuthor(author).build();
        NewsUpdateInfo info = new NewsUpdateInfo("newTitle", null, null, null);

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        assertThatThrownBy(() -> newsDomainService.update(1L, "otherUser", info))
                .isInstanceOf(NewsAccessDeniedException.class);
    }

    @Test
    @DisplayName("뉴스 삭제 성공")
    void shouldDeleteNewsSuccessfully() {
        String authorId = "author1";
        com.fivelogic_recreate.member.domain.model.Member author = memberFixture.withUserId(authorId).build();
        News news = newsFixture.withAuthor(author).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        News deletedNews = newsDomainService.delete(1L, authorId);

        assertThat(deletedNews.getStatus()).isEqualTo(NewsStatus.DELETED);
    }

    @Test
    @DisplayName("뉴스 발행 성공")
    void shouldPublishNewsSuccessfully() {
        String authorId = "author1";
        com.fivelogic_recreate.member.domain.model.Member author = memberFixture.withUserId(authorId).build();
        News news = newsFixture.withAuthor(author).withStatus(NewsStatus.READY).build();

        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.of(news));

        News publishedNews = newsDomainService.publish(1L, authorId);

        assertThat(publishedNews.getStatus()).isEqualTo(NewsStatus.PUBLISHED);
        assertThat(publishedNews.getPublishedDate()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 뉴스 접근 시 예외 발생")
    void shouldThrowExceptionWhenNewsNotFound() {
        when(newsRepositoryPort.findById(any(NewsId.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsDomainService.delete(1L, "anyUser"))
                .isInstanceOf(NewsNotFoundException.class);
    }
}
