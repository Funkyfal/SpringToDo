package com.emobile.springtodo.service;

import com.emobile.springtodo.model.entity.Todo;
import com.emobile.springtodo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Optional<Todo> findTodo(Long id) {
        return todoRepository.findById(id);
    }

    public List<Todo> findAllTodo() {
        return todoRepository.findAll();
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo, Long id) {
        return todoRepository.update(todo, id);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
