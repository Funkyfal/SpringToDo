package com.emobile.springtodo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = TodoCreateDto.Builder.class)
public record TodoCreateDto(
        @NotBlank(message = "Title must not be blank.")
        String title,
        String description,
        @FutureOrPresent(message = "Due date must be now or in the future.")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime due_date
) {
        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {}
}
