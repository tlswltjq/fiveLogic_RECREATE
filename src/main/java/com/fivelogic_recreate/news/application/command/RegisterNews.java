package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.NewsServicePolicyValidator;

import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.domain.News;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterNews {
    private final NewsStore newsStore;
    private final NewsServicePolicyValidator validator;

    public NewsCreateResult createNews(NewsCreateCommand command) {
        validator.checkRegisterPolicy(command);

        News draft = News.draft(command.title(), command.description(), command.textContent(), command.videoUrl(),
                new UserId(command.authorId()));

        draft.processing();
        News saved = newsStore.store(draft);
        return NewsCreateResult.from(saved);
    }
}
