package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;

public record PublishNewsRequest() {
    public NewsPublishCommand toCommand(Long id, String userId) {
        return new NewsPublishCommand(id, userId);
    }
}
