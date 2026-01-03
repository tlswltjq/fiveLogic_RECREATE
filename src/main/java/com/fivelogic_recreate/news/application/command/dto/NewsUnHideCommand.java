package com.fivelogic_recreate.news.application.command.dto;

public record NewsUnHideCommand(
                Long newsId,
                String currentUserId) {
}
