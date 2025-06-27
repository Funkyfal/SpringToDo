package com.emobile.springtodo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TodoDto(
        @NotBlank
        String title,
        String description,
        LocalDateTime due_date,
        boolean completed
) {}
