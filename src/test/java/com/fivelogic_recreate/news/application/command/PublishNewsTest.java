package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.application.NewsServicePolicyValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PublishNewsTest {

    @InjectMocks
    private PublishNews publishNews;

    @Mock
    private NewsReader newsReader;

    @Mock
    private NewsStore newsStore;

    @Mock
    private NewsServicePolicyValidator validator;

    @Test
    @DisplayName("뉴스가 성공적으로 발행되어야 한다")
    void publishNews_success() {
        // given
        Long newsId = 1L;
        String authorId = "authorId";
        NewsPublishCommand command = new NewsPublishCommand(newsId, authorId);

        Member author = Member.join(authorId, "password", "email@test.com", "First", "Last", "Nick", "Bio");
        News news = News.draft("title", "desc", "content", "url", author.getUserId());
        news.processing();
        news.ready();

        given(newsReader.getNews(newsId)).willReturn(news);

        // when
        NewsPublishResult result = publishNews.publishNews(command);

        // then
        verify(newsStore).store(news);
        assertThat(news.getStatus()).isEqualTo(NewsStatus.PUBLISHED);
        assertThat(news.getPublishedDate()).isNotNull();
    }
}
