package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsCreateService {
    private final NewsRepositoryPort newsRepositoryPort;

    public NewsCreateResult createNews(NewsCreateCommand command) {
        News draft = News.draft(command.title(), command.description(), command.textContent(), command.videoUrl(), command.authorId());

        draft.processing();
        News saved = newsRepositoryPort.save(draft);
        return new NewsCreateResult(saved.getId().value(), saved.getTitle().value(), saved.getAuthorId().value(),saved.getStatus());
    }
}
