package com.fivelogic_recreate.news.application.command.dto;

public record NewsCreateCommand(
        String title,
        String description,
        String textContent,
        String videoUrl,
        String authorId
) {
}
