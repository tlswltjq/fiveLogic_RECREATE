package com.fivelogic_recreate.news.application;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsReader {
    private final NewsRepositoryPort repository;

    public News getNews(Long newsId) {
        return repository.findById(new NewsId(newsId))
                .orElseThrow(NewsNotFoundException::new);
    }
}
