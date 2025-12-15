package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsInfo;
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

    public NewsInfo createNews(NewsCreateCommand command) {
        News draft = News.draft(command.title(), command.description(), command.textContent(), command.videoUrl(), command.authorId());

        draft.processing();

        return new NewsInfo(newsRepositoryPort.save(draft));
    }
}
