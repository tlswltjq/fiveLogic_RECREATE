package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.NewsServicePolicyValidator;

import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EditNews {
    private final NewsReader newsReader;
    private final NewsStore newsStore;
    private final NewsServicePolicyValidator validator;

    public NewsUpdateResult updateNews(NewsUpdateCommand command) {
        validator.checkEditPolicy(command);
        News news = newsReader.getNews(command.id());
        news.validateOwner(command.currentUserId());

        if (command.title() != null) {
            news.changeTitle(new Title(command.title()));
        }
        if (command.description() != null) {
            news.changeDescription(new Description(command.description()));
        }
        if (command.textContent() != null) {
            news.changeTextContent(new TextContent(command.textContent()));
        }
        if (command.videoUrl() != null) {
            news.changeVideoUrl(new VideoUrl(command.videoUrl()));
        }

        newsStore.store(news);

        return NewsUpdateResult.from(news);
    }
}
