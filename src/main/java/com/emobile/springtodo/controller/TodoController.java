package com.emobile.springtodo.controller;

import com.emobile.springtodo.model.entity.Todo;
import com.emobile.springtodo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("todo/{id}")
    public Optional<Todo> getTodo(@PathVariable Long id) {
        return todoService.findTodo(id);
    }

    @GetMapping("todo")
    public List<Todo> getAllTodo() {
        return todoService.findAllTodo();
    }

    @PostMapping("todo")
    public Todo saveTodo(@RequestBody Todo todo) {
        return todoService.saveTodo(todo);
    }

    @PutMapping("todo/{id}")
    public Todo updateTodo(@PathVariable Long id,@RequestBody Todo todo) {
        return todoService.updateTodo(todo, id);
    }

    @DeleteMapping("todo/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}
