package com.emobile.springtodo.configuration;

import com.emobile.springtodo.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Todo API", description = "Управление задачами")
@RequestMapping("/todo")
public interface TodoApi {

    @Operation(summary = "Создать новую задачу")
    @PostMapping
    TodoDto create(
            @Valid @RequestBody TodoCreateDto dto
    );

    @Operation(summary = "Получить список задач с пагинацией")
    @GetMapping
    List<TodoDto> list(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    );

    @Operation(summary = "Получить задачу по ID")
    @GetMapping("/{id}")
    TodoDto getById(
            @Parameter(description = "ID задачи") @PathVariable Long id
    );

    @Operation(summary = "Обновить задачу")
    @PutMapping("/{id}")
    TodoDto update(
            @PathVariable Long id,
            @Valid @RequestBody TodoUpdateDto dto
    );

    @Operation(summary = "Удалить задачу")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @Operation(summary = "Выполнить задачу")
    @PutMapping("/complete/{id}")
    TodoDto completeById(@PathVariable Long id);

    @Operation(summary = "Отменить выполнение задачи")
    @PutMapping("/incomplete/{id}")
    TodoDto incompleteById(@PathVariable Long id);
}
