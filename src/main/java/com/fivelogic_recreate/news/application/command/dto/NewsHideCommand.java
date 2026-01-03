package com.fivelogic_recreate.news.application.command.dto;

public record NewsHideCommand(
                Long newsId,
                String currentUserId) {
}
