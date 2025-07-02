package com.emobile.springtodo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = TodoDto.Builder.class)
public record TodoDto(
        @NotNull
        Long id,
        @NotBlank
        String title,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime due_date,
        boolean completed
) {
        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {}
}
