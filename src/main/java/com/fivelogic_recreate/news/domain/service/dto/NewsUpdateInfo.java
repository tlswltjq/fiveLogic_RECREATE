package com.fivelogic_recreate.news.domain.service.dto;

public record NewsUpdateInfo(
        String title,
        String description,
        String textContent,
        String videoUrl) {
}
