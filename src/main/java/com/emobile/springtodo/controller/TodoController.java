package com.emobile.springtodo.controller;

import com.emobile.springtodo.model.dto.TodoCreateDto;
import com.emobile.springtodo.model.dto.TodoDto;
import com.emobile.springtodo.model.dto.TodoUpdateDto;
import com.emobile.springtodo.service.TodoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TodoController {
    private final TodoServiceImpl todoService;

    @GetMapping("todo/{id}")
    public TodoDto getTodo(@PathVariable Long id) {
        return todoService.findTodo(id);
    }

    @GetMapping("todo")
    public List<TodoDto> getAllTodo() {
        return todoService.findAllTodo();
    }

    @PostMapping("todo")
    public TodoDto saveTodo(@RequestBody TodoCreateDto todo) {
        return todoService.saveTodo(todo);
    }

    @PutMapping("todo/{id}")
    public TodoDto updateTodo(@PathVariable Long id,@RequestBody TodoUpdateDto todo) {
        return todoService.updateTodo(todo, id);
    }

    @DeleteMapping("todo/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @PutMapping("todo/complete/{id}")
    public TodoDto completeById(@PathVariable Long id) {
        return todoService.completeById(id);
    }

    @PutMapping("todo/incomplete/{id}")
    public TodoDto incompleteById(@PathVariable Long id) {
        return todoService.incompleteById(id);
    }
}
