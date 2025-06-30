package com.emobile.springtodo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = TodoUpdateDto.Builder.class)
public record TodoUpdateDto(
        String title,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime due_date,
        Boolean completed
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {}
}
