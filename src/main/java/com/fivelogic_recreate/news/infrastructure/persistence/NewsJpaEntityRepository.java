package com.fivelogic_recreate.news.infrastructure.persistence;

import com.fivelogic_recreate.news.application.query.dto.NewsQueryResponse;
import com.fivelogic_recreate.news.domain.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NewsJpaEntityRepository extends JpaRepository<NewsJpaEntity, Long> {
    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.id = :id")
    Optional<NewsQueryResponse> findQueryById(@Param("id") Long id);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.title = :title")
    List<NewsQueryResponse> findQueryByTitle(@Param("title") String title);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.content LIKE %:keyword%")
    List<NewsQueryResponse> findQueryByContentContaining(@Param("keyword") String keyword);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.author.userId = :authorUserId")
    List<NewsQueryResponse> findQueryByAuthor_UserId(@Param("authorUserId") String authorUserId);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.status = :status")
    List<NewsQueryResponse> findQueryByStatus(@Param("status") NewsStatus status);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.publishedDate > :publishedDate")
    Page<NewsQueryResponse> findQueryByPublishedDateAfter(@Param("publishedDate") LocalDateTime publishedDate, Pageable pageable);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.publishedDate < :publishedDate")
    Page<NewsQueryResponse> findQueryByPublishedDateBefore(@Param("publishedDate") LocalDateTime publishedDate, Pageable pageable);

    @Query("SELECT n.id as id, n.title AS title, n.description AS description, n.content AS content, n.videoUrl AS videoUrl, n.author.userId AS authorId, n.publishedDate AS publishedDate, n.status AS status FROM NewsJpaEntity n WHERE n.publishedDate BETWEEN :startDate AND :endDate")
    Page<NewsQueryResponse> findQueryByPublishedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}

