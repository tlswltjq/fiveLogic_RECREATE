package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsUpdateCommand;
import jakarta.validation.constraints.NotBlank;

public record UpdateNewsRequest(
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotBlank(message = "설명은 필수입니다.")
        String description,
        @NotBlank(message = "본문은 필수입니다.")
        String textContent,
        @NotBlank(message = "비디오 URL은 필수입니다.")
        String videoUrl
) {
    public NewsUpdateCommand toCommand(Long id, String userId) {
        return new NewsUpdateCommand(id, title, description, textContent, videoUrl, userId);
    }
}
