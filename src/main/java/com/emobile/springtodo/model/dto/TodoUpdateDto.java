package com.emobile.springtodo.model.dto;

import java.time.LocalDateTime;

public record TodoUpdateDto(
        String title,
        String description,
        LocalDateTime due_date,
        boolean completed
) {
}
