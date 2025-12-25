package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import com.fivelogic_recreate.news.exception.NewsPublishNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsPublishService {
    private final NewsRepositoryPort newsRepositoryPort;

    public NewsPublishResult publishNews(NewsPublishCommand command) {
        NewsId newsId = new NewsId(command.newsId());
        News news = newsRepositoryPort.findById(newsId).orElseThrow(NewsNotFoundException::new);

        try {
            news.publish();
        } catch (IllegalStateException e) {
            throw new NewsPublishNotAllowedException();
        }
        return new NewsPublishResult(news.getId(), news.getTitle().value(), news.getPublishedDate(), news.getStatus());
    }
}
