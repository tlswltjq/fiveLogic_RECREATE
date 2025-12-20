package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.member.infrastructure.persistence.MemberJpaEntity;
import com.fivelogic_recreate.member.infrastructure.persistence.MemberJpaRepository;
import com.fivelogic_recreate.news.domain.AuthorId;
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

    //TODO 행위가 필요없는 조회 유스케이스들이 애그리게이트 루트를 통과하고있음 읽기 모델 도입과 프로젝션으로 리펙토링 필요
    @Override
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id)
                .map(NewsJpaEntity::toDomain);
    }

    @Override
    public List<News> findByTitle(String title) {
        return newsRepository.findByTitle(title).stream()
                .map(NewsJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<News> findByContent(String textContent) {
        return newsRepository.findByContentContaining(textContent).stream()
                .map(NewsJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<News> findByAuthorId(String authorId) {
        return newsRepository.findByAuthor_UserId(authorId).stream()
                .map(NewsJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<News> findByNewsStatus(String status) {
        NewsStatus newsStatus = NewsStatus.from(status);
        return newsRepository.findByStatus(newsStatus).stream()
                .map(NewsJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Page<News> findByPublishedDateAfter(LocalDateTime publishedDate, Pageable pageable) {
        return newsRepository
                .findByPublishedDateAfter(publishedDate, pageable)
                .map(NewsJpaEntity::toDomain);
    }

    @Override
    public Page<News> findByPublishedDateBefore(LocalDateTime publishedDate, Pageable pageable) {
        return newsRepository
                .findByPublishedDateBefore(publishedDate, pageable)
                .map(NewsJpaEntity::toDomain);
    }

    @Override
    public Page<News> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return newsRepository
                .findByPublishedDateBetween(startDate, endDate, pageable)
                .map(NewsJpaEntity::toDomain);
    }

    @Override
    public News save(News news) {
        MemberJpaEntity memberEntity = memberRepository.findByUserId(news.getAuthorId().value()).orElseThrow(MemberNotFoundException::new);
        NewsJpaEntity newsEntity = NewsJpaEntity.from(news, memberEntity);
        return newsRepository.save(newsEntity).toDomain();
    }

    @Override
    public Optional<News> findById(NewsId id) {
        //프로젝션 적용 전 까지 땜빵
        return findById(id.value());
    }

    @Override
    public List<News> findAll() {
        //TODO
        return List.of();
    }

    @Override
    public List<News> findOwnedBy(AuthorId authorId) {
        //TODO
        return List.of();
    }

    @Override
    public List<News> findByStatus(NewsStatus status) {
        //TODO
        return List.of();
    }

    @Override
    public void deleteById(NewsId id) {
        //TODO
    }
}
