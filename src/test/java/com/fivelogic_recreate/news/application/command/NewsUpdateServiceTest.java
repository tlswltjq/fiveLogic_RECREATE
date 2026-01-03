package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.fixture.News.NewsFixture;
import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.service.MemberDomainService;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsUpdateServiceTest {
    @Mock
    private NewsDomainService newsDomainService;
    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private NewsUpdateService newsUpdateService;

    private final NewsFixture newsFixture = new NewsFixture();

    @Test
    @DisplayName("뉴스를 성공적으로 수정한다.")
    void shouldUpdateNewsSuccessfully() {
        Long newsId = 1L;
        Member author = new MemberFixture().withUserId("author-1").build();
        News updatedNews = newsFixture.withId(newsId).withAuthor(author)
                .withTitle("Updated Title")
                .withDescription("Updated Description")
                .withTextContent("Updated Content")
                .withVideoUrl("updated-video-url.com")
                .build();

        NewsUpdateCommand command = new NewsUpdateCommand(
                newsId,
                "Updated Title",
                "Updated Description",
                "Updated Content",
                "updated-video-url.com",
                "author-1");

        when(memberDomainService.getMember(any())).thenReturn(author);
        when(newsDomainService.update(any(), any(), any())).thenReturn(updatedNews);

        NewsUpdateResult result = newsUpdateService.updateNews(command);

        assertThat(result.title()).isEqualTo("Updated Title");
        assertThat(result.description()).isEqualTo("Updated Description");
        assertThat(result.textContent()).isEqualTo("Updated Content");
        assertThat(result.videoUrl()).isEqualTo("updated-video-url.com");
    }

    @Test
    @DisplayName("일부 필드만 수정할 경우 해당 필드만 변경된다.")
    void shouldUpdatePartialFields() {
        Long newsId = 1L;
        Member author = new MemberFixture().withUserId("author-1").build();
        News partiallyUpdatedNews = newsFixture.withId(newsId).withAuthor(author)
                .withTitle("Updated Title")
                .withDescription("Original Description")
                .build();

        NewsUpdateCommand command = new NewsUpdateCommand(
                newsId,
                "Updated Title",
                null,
                null,
                null,
                "author-1");

        when(memberDomainService.getMember(any())).thenReturn(author);
        when(newsDomainService.update(any(), any(), any())).thenReturn(partiallyUpdatedNews);

        NewsUpdateResult result = newsUpdateService.updateNews(command);

        assertThat(result.title()).isEqualTo("Updated Title");
        assertThat(result.description()).isEqualTo("Original Description");
    }
}
