package com.fivelogic_recreate.member.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InfoUpdateRequest(
                @NotBlank String nickname,
                @NotNull String bio) {
}
