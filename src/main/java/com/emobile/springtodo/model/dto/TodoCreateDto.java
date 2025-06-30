package com.emobile.springtodo.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TodoCreateDto(
        @NotBlank(message = "Title must not be blank.")
        String title,
        String description,
        @FutureOrPresent(message = "Due date must be now or in the future.")
        LocalDateTime due_date
) {}
