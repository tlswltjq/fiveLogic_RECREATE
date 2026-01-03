package com.fivelogic_recreate.news.domain.service;

import com.fivelogic_recreate.news.domain.*;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.domain.service.dto.NewsUpdateInfo;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsDomainService {
    private final NewsRepositoryPort newsRepository;

    public News create(News news) {
        return newsRepository.save(news);
    }

    public News update(Long newsId, String currentUserId, NewsUpdateInfo info) {
        News news = getNewsById(newsId);
        news.validateOwner(currentUserId);

        if (info.title() != null) {
            news.changeTitle(new Title(info.title()));
        }
        if (info.description() != null) {
            news.changeDescription(new Description(info.description()));
        }
        if (info.textContent() != null) {
            news.changeTextContent(new TextContent(info.textContent()));
        }
        if (info.videoUrl() != null) {
            news.changeVideoUrl(new VideoUrl(info.videoUrl()));
        }

        return news;
    }

    public News delete(Long newsId, String currentUserId) {
        News news = getNewsById(newsId);
        news.validateOwner(currentUserId);
        news.delete();
        return news;
    }

    public News publish(Long newsId, String currentUserId) {
        News news = getNewsById(newsId);
        news.validateOwner(currentUserId);
        news.publish();
        return news;
    }

    public News hide(Long newsId, String currentUserId) {
        News news = getNewsById(newsId);
        news.validateOwner(currentUserId);
        news.hide();
        return news;
    }

    public News unhide(Long newsId, String currentUserId) {
        News news = getNewsById(newsId);
        news.validateOwner(currentUserId);
        news.unhide();
        return news;
    }

    private News getNewsById(Long newsId) {
        return newsRepository.findById(new NewsId(newsId))
                .orElseThrow(NewsNotFoundException::new);
    }
}
