package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsHideCommand;

public record HideNewsRequest() {
    public NewsHideCommand toCommand(Long id) {
        return new NewsHideCommand(id);
    }
}
