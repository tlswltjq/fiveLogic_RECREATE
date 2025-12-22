package com.fivelogic_recreate.news.domain.port;

import com.fivelogic_recreate.news.domain.AuthorId;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;

import java.util.List;
import java.util.Optional;

public interface NewsRepositoryPort {
    News save(News news);
    Optional<News> findById(NewsId id);
    List<News> findAll();
    List<News> findOwnedBy(AuthorId authorId);
    List<News> findByStatus(NewsStatus status);
}
