package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.domain.News;
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
class NewsUpdateServiceTest {

    @InjectMocks
    private NewsUpdateService newsUpdateService;

    @Mock
    private NewsReader newsReader;

    @Mock
    private NewsStore newsStore;

    @Test
    @DisplayName("뉴스가 성공적으로 수정되어야 한다")
    void updateNews_success() throws Exception {
        // given
        Long newsId = 1L;
        String authorId = "authorId";
        NewsUpdateCommand command = new NewsUpdateCommand(
                newsId,
                "newTitle",
                "newDescription",
                "newContent",
                "newVideoUrl",
                authorId);

        Member author = Member.join(authorId, "password", "email@test.com", "First", "Last", "Nick", "Bio");
        News news = News.draft("title", "desc", "content", "url", author);

        // Reflection to set ID if needed, but here we just mock return
        // Since News.equals uses ID, finding by ID relies on ID.
        // But NewsReader returns the object.

        given(newsReader.getNews(newsId)).willReturn(news);

        // when
        NewsUpdateResult result = newsUpdateService.updateNews(command);

        // then
        verify(newsStore).store(news);
        assertThat(result.title()).isEqualTo("newTitle");
        assertThat(result.description()).isEqualTo("newDescription");
    }
}
