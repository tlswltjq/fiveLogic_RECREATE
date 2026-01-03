package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsUnHideCommand;

public record UnhideNewsRequest() {
    public NewsUnHideCommand toCommand(Long id, String userId) {
        return new NewsUnHideCommand(id, userId);
    }
}
