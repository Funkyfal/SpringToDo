package com.emobile.springtodo.service;

import com.emobile.springtodo.model.dto.TodoCreateDto;
import com.emobile.springtodo.model.dto.TodoDto;
import com.emobile.springtodo.model.dto.TodoUpdateDto;

import java.util.List;

public interface TodoService {
    TodoDto findTodo(Long id);
    List<TodoDto> findAllTodo();
    TodoDto saveTodo(TodoCreateDto incoming);
    TodoDto updateTodo(TodoUpdateDto incoming, Long id);
    void deleteTodo(Long id);
    TodoDto completeById(Long id);
    TodoDto incompleteById(Long id);
    boolean existsById(Long id);
}
