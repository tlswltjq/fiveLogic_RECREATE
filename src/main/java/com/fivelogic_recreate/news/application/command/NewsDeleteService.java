package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.news.application.command.dto.NewsDeleteCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsInfo;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.NewsId;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import com.fivelogic_recreate.news.exception.NewsDeleteNotAllowedException;
import com.fivelogic_recreate.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsDeleteService {
    private final NewsRepositoryPort newsRepositoryPort;

    public NewsInfo deleteNews(NewsDeleteCommand command) {
        NewsId newsId = new NewsId(command.newsId());
        News news = newsRepositoryPort.findById(newsId).orElseThrow(NewsNotFoundException::new);

        try {
            news.delete();
        }catch (IllegalStateException e) {
            throw new NewsDeleteNotAllowedException();
        }

        return new NewsInfo(newsRepositoryPort.save(news));
    }
}
