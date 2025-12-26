package com.fivelogic_recreate.news.interfaces.rest.dto;

import com.fivelogic_recreate.news.application.command.dto.NewsCreateCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateNewsRequest(
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotBlank(message = "설명은 필수입니다.")
        String description,
        @NotBlank(message = "본문은 필수입니다.")
        String textContent,
        @NotBlank(message = "비디오 URL은 필수입니다.")
        String videoUrl,
        @NotBlank(message = "작성자 ID는 필수입니다.")
        String authorId
) {
    public NewsCreateCommand toCommand() {
        return new NewsCreateCommand(title, description, textContent, videoUrl, authorId);
    }
}
