package com.fivelogic_recreate.news.application;

import com.fivelogic_recreate.news.application.command.dto.*;
import com.fivelogic_recreate.news.domain.port.NewsQueryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsServicePolicyValidator {
    private final NewsQueryRepositoryPort queryRepository;

    public void checkRegisterPolicy(NewsCreateCommand command) {
        // TODO: Add policy checks (e.g. author existence, content safety, etc)
    }

    public void checkEditPolicy(NewsUpdateCommand command) {
        // TODO: Add policy checks
    }

    public void checkPublishPolicy(NewsPublishCommand command) {
        // TODO: Add policy checks
    }

    public void checkHidePolicy(NewsHideCommand command) {
        // TODO: Add policy checks
    }

    public void checkUnhidePolicy(NewsUnHideCommand command) {
        // TODO: Add policy checks
    }

    public void checkRemovePolicy(NewsDeleteCommand command) {
        // TODO: Add policy checks
    }
}
