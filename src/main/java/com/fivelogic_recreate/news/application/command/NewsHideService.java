package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.exception.NewsHideNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsHideService {
    private final NewsReader newsReader;
    private final NewsStore newsStore;

    public NewsHideResult hideNews(NewsHideCommand command) {
        News news = newsReader.getNews(command.newsId());
        news.validateOwner(command.currentUserId());

        try {
            news.hide();
        } catch (IllegalStateException e) {
            throw new NewsHideNotAllowedException();
        }

        newsStore.store(news);

        return NewsHideResult.from(news);
    }
}
