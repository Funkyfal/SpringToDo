package com.emobile.springtodo.model.mapper;

import com.emobile.springtodo.model.dto.TodoCreateDto;
import com.emobile.springtodo.model.dto.TodoDto;
import com.emobile.springtodo.model.dto.TodoUpdateDto;
import com.emobile.springtodo.model.entity.Todo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TodoMapper {

    Todo toEntity(TodoCreateDto dto);
    TodoDto toDto(Todo entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TodoUpdateDto dto, @MappingTarget Todo entity);
}
