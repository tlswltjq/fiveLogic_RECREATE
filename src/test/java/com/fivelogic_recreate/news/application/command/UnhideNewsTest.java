package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
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
class UnhideNewsTest {

    @InjectMocks
    private UnhideNews unhideNews;

    @Mock
    private NewsReader newsReader;

    @Mock
    private NewsStore newsStore;

    @Mock
    private NewsServicePolicyValidator validator;

    @Test
    @DisplayName("뉴스가 성공적으로 숨김 해제되어야 한다")
    void unHideNews_success() {
        // given
        Long newsId = 1L;
        String authorId = "authorId";
        NewsUnHideCommand command = new NewsUnHideCommand(newsId, authorId);

        Member author = Member.join(authorId, "password", "email@test.com", "First", "Last", "Nick", "Bio");
        News news = News.draft("title", "desc", "content", "url", author.getUserId());
        news.processing();
        news.ready();
        news.publish();
        news.hide();

        given(newsReader.getNews(newsId)).willReturn(news);

        // when
        NewsHideResult result = unhideNews.unHideNews(command);

        // then
        verify(newsStore).store(news);
        assertThat(news.getStatus()).isEqualTo(NewsStatus.PUBLISHED);
    }
}
