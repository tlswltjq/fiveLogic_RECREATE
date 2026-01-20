package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsPublishResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.exception.NewsPublishNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsPublishService {
    private final NewsReader newsReader;
    private final NewsStore newsStore;

    public NewsPublishResult publishNews(NewsPublishCommand command) {
        News news = newsReader.getNews(command.newsId());
        news.validateOwner(command.currentUserId());

        try {
            news.publish();
        } catch (IllegalStateException e) {
            throw new NewsPublishNotAllowedException();
        }

        newsStore.store(news);

        return NewsPublishResult.from(news);
    }
}
