package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.NewsReader;
import com.fivelogic_recreate.news.application.NewsStore;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.exception.NewsDeleteNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsDeleteService {
    private final NewsReader newsReader;
    private final NewsStore newsStore;

    public NewsDeleteResult deleteNews(NewsDeleteCommand command) {
        News news = newsReader.getNews(command.newsId());
        news.validateOwner(command.currentUserId());

        try {
            news.delete();
        } catch (IllegalStateException e) {
            throw new NewsDeleteNotAllowedException();
        }

        newsStore.store(news);

        return NewsDeleteResult.from(news);
    }
}
