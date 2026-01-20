package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.application.NewsServicePolicyValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterNewsTest {

    @InjectMocks
    private RegisterNews registerNews;

    @Mock
    private NewsStore newsStore;

    @Mock
    private NewsServicePolicyValidator validator;

    @Test
    @DisplayName("뉴스가 성공적으로 생성되어야 한다")
    void createNews_success() {
        // given
        NewsCreateCommand command = new NewsCreateCommand(
                "title",
                "description",
                "content",
                "videoUrl",
                "authorId");

        // given(memberReader.getMember(command.authorId())).willReturn(author); //
        // Removed
        given(newsStore.store(any(News.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        NewsCreateResult result = registerNews.createNews(command);

        // then
        // verify(memberReader).getMember(command.authorId()); // Removed
        verify(newsStore).store(any(News.class));

        assertThat(result).isNotNull();
        // Additional assertions can be added here
    }
}
