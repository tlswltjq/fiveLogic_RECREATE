package com.fivelogic_recreate.news.domain.port;

import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;

import java.util.Optional;

public interface NewsRepositoryPort {
    News save(News news);
    Optional<News> findById(NewsId id);
}
