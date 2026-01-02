package com.fivelogic_recreate.news.application.command;

import com.fivelogic_recreate.member.domain.model.Member;
import com.fivelogic_recreate.member.domain.model.UserId;
import com.fivelogic_recreate.member.domain.port.MemberQueryRepositoryPort;
import com.fivelogic_recreate.member.exception.MemberNotFoundException;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import com.fivelogic_recreate.news.application.command.dto.NewsCreateResult;
import com.fivelogic_recreate.news.domain.News;
import com.fivelogic_recreate.news.domain.port.NewsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsCreateService {
    private final NewsRepositoryPort newsRepositoryPort;
    private final MemberQueryRepositoryPort memberQueryRepositoryPort;

    public NewsCreateResult createNews(NewsCreateCommand command) {
        Member author = memberQueryRepositoryPort.findByUserId(new UserId(command.authorId()))
                .orElseThrow(MemberNotFoundException::new);

        News draft = News.draft(command.title(), command.description(), command.textContent(), command.videoUrl(), author);

        draft.processing();
        News saved = newsRepositoryPort.save(draft);
        return new NewsCreateResult(saved.getId(), saved.getTitle().value(),
                saved.getAuthor().getUserId().value(),
                saved.getStatus());
    }
}
