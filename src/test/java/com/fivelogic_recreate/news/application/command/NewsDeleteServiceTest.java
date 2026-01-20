package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsStatus;
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
class NewsDeleteServiceTest {

    @InjectMocks
    private NewsDeleteService newsDeleteService;

    @Mock
    private NewsReader newsReader;

    @Mock
    private NewsStore newsStore;

    @Test
    @DisplayName("뉴스가 성공적으로 삭제 처리되어야 한다")
    void deleteNews_success() {
        // given
        Long newsId = 1L;
        String authorId = "authorId";
        NewsDeleteCommand command = new NewsDeleteCommand(newsId, authorId);

        Member author = Member.join(authorId, "password", "email@test.com", "First", "Last", "Nick", "Bio");
        News news = News.draft("title", "desc", "content", "url", author);

        given(newsReader.getNews(newsId)).willReturn(news);

        // when
        NewsDeleteResult result = newsDeleteService.deleteNews(command);

        // then
        verify(newsStore).store(news);
        assertThat(news.getStatus()).isEqualTo(NewsStatus.DELETED);
        assertThat(result).isNotNull();
    }
}
