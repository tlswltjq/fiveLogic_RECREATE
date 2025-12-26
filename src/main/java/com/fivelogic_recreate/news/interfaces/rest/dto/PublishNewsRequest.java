package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsPublishCommand;

public record PublishNewsRequest() {
    public NewsPublishCommand toCommand(Long id) {
        return new NewsPublishCommand(id);
    }
}
