package com.fivelogic_recreate.news.application.command.dto;

public record NewsUpdateCommand(
        Long id,
        String title,
        String description,
        String textContent,
        String videoUrl) {
}
