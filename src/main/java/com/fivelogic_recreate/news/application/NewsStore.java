package com.fivelogic_recreate.news.application;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsStore {
    private final NewsRepositoryPort repository;

    public News store(News news) {
        return repository.save(news);
    }
}
