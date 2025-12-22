package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.member.infrastructure.persistence.MemberJpaEntity;
import com.fivelogic_recreate.member.infrastructure.persistence.MemberJpaRepository;
import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.NewsStatus;
import com.fivelogic_recreate.news.domain.port.NewsQueryRepositoryPort;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsJpaRepositoryImpl implements NewsRepositoryPort, NewsQueryRepositoryPort {
    private final NewsJpaEntityRepository newsRepository;
    private final MemberJpaRepository memberRepository;

    @Override
    public Optional<NewsQueryResponse> findQueryById(Long id) {
        return newsRepository.findQueryById(id);
    }

    @Override
    public List<NewsQueryResponse> findByTitle(String title) {
        return newsRepository.findQueryByTitle(title);
    }

    @Override
    public List<NewsQueryResponse> findByContent(String textContent) {
        return newsRepository.findQueryByContentContaining(textContent);
    }

    @Override
    public List<NewsQueryResponse> findByAuthorId(String authorId) {
        return newsRepository.findQueryByAuthor_UserId(authorId);
    }

    @Override
    public List<NewsQueryResponse> findByNewsStatus(String status) {
        NewsStatus newsStatus = NewsStatus.from(status);
        return newsRepository.findQueryByStatus(newsStatus);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable) {
        return newsRepository
                .findQueryByPublishedDateAfter(publishedDate, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable) {
        return newsRepository
                .findQueryByPublishedDateBefore(publishedDate, pageable);
    }

    @Override
    public Page<NewsQueryResponse> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return newsRepository
                .findQueryByPublishedDateBetween(startDate, endDate, pageable);
    }

    @Override
    public News save(News news) {
        MemberJpaEntity memberEntity = memberRepository.findByUserId(news.getAuthorId().value()).orElseThrow(MemberNotFoundException::new);
        NewsJpaEntity newsEntity = NewsJpaEntity.from(news, memberEntity);
        return newsRepository.save(newsEntity).toDomain();
    }

    @Override
    public Optional<News> findById(NewsId id) {
        return newsRepository.findById(id.value()).map(NewsJpaEntity::toDomain);
    }
}
