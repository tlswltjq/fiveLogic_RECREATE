package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsHideNotAllowedException;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsHideService {
    private final NewsRepositoryPort newsRepositoryPort;

    public NewsHideResult hideNews(NewsHideCommand command) {
        NewsId newsId = new NewsId(command.newsId());
        News news = newsRepositoryPort.findById(newsId).orElseThrow(NewsNotFoundException::new);

        try {
            news.hide();
        } catch (IllegalStateException e) {
            throw new NewsHideNotAllowedException();
        }
        return new NewsHideResult(news.getId(), news.getTitle().value(), news.getStatus());
    }
}
