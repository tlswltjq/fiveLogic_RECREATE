package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;

public record UpdateNewsRequest(
        String title,
        String description,
        String textContent,
        String videoUrl) {
    public NewsUpdateCommand toCommand(Long id) {
        return new NewsUpdateCommand(id, title, description, textContent, videoUrl);
    }
}
