package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsHideResult;
import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.service.NewsDomainService;
import com.fivelogic_recreate.news.exception.NewsUnHideNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsUnHideService {
    private final NewsDomainService newsDomainService;

    public NewsHideResult unHideNews(NewsUnHideCommand command) {
        News news;
        try {
            news = newsDomainService.unhide(command.newsId(), command.currentUserId());
        } catch (IllegalStateException e) {
            throw new NewsUnHideNotAllowedException();
        }
        return new NewsHideResult(news.getId(), news.getTitle().value(), news.getStatus());
    }
}
