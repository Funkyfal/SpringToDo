package com.emobile.springtodo.controller;

import com.emobile.springtodo.model.dto.TodoCreateDto;
import com.emobile.springtodo.model.dto.TodoDto;
import com.emobile.springtodo.model.dto.TodoUpdateDto;
import com.emobile.springtodo.service.implementation.TodoServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/todo")
public class TodoController implements TodoApi {
    private final TodoServiceImpl todoService;

    @GetMapping("/{id}")
    @Override
    public TodoDto getById(@PathVariable Long id) {
        return todoService.findTodo(id);
    }

    @GetMapping
    @Override
    public List<TodoDto> list(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return todoService.findAllTodo(limit, offset);
    }

    @PostMapping
    @Override
    public TodoDto create(
            @Valid
            @RequestBody TodoCreateDto todo) {
        return todoService.saveTodo(todo);
    }

    @PutMapping("/{id}")
    @Override
    public TodoDto update(
            @PathVariable Long id,
            @Valid
            @RequestBody TodoUpdateDto todo) {
        return todoService.updateTodo(todo, id);
    }

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @PutMapping("/complete/{id}")
    @Override
    public TodoDto completeById(@PathVariable Long id) {
        return todoService.completeById(id);
    }

    @PutMapping("/incomplete/{id}")
    @Override
    public TodoDto incompleteById(@PathVariable Long id) {
        return todoService.incompleteById(id);
    }
}
