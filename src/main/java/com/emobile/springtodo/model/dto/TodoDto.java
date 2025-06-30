package com.emobile.springtodo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TodoDto(
        @NotNull
        Long id,
        @NotBlank
        String title,
        String description,
        LocalDateTime due_date,
        boolean completed
) {}
