package com.emobile.springtodo.repository;

import com.emobile.springtodo.model.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    Optional<Todo> findById(Long id);
    List<Todo> findAll();
    Todo save(Todo todo);
    Todo update(Todo todo, Long id);
    void deleteById(Long id);

    Todo completeById(Long id);
    Todo incompleteById(Long id);
    boolean existsById(Long id);
}
