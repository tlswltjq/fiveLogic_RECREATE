package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsDeleteResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import com.fivelogic_recreate.news.exception.NewsDeleteNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsDeleteService {
    private final NewsDomainService newsDomainService;

    public NewsDeleteResult deleteNews(NewsDeleteCommand command) {
        News news;
        try {
            news = newsDomainService.delete(command.newsId(), command.currentUserId());
        } catch (IllegalStateException e) {
            throw new NewsDeleteNotAllowedException();
        }

        return new NewsDeleteResult(news.getId(), news.getTitle().value(), news.getStatus());
    }
}
