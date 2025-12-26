package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsUpdateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsUpdateService {
    private final NewsRepositoryPort newsRepositoryPort;

    public NewsUpdateResult updateNews(NewsUpdateCommand command) {
        News news = newsRepositoryPort.findById(new NewsId(command.id()))
                .orElseThrow(NewsNotFoundException::new);

        if (command.title() != null) {
            news.changeTitle(command.title());
        }
        if (command.description() != null) {
            news.changeDescription(command.description());
        }
        if (command.textContent() != null) {
            news.changeTextContent(command.textContent());
        }
        if (command.videoUrl() != null) {
            news.changeVideoUrl(command.videoUrl());
        }

        return new NewsUpdateResult(
                news.getId(),
                news.getTitle().value(),
                news.getDescription().value(),
                news.getTextContent().value(),
                news.getVideoUrl().value(),
                news.getStatus());
    }
}
