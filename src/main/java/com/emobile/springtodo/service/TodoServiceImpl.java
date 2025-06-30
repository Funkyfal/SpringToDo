package com.emobile.springtodo.service;

import com.emobile.springtodo.exception.TodoNotFoundException;
import com.emobile.springtodo.model.dto.TodoCreateDto;
import com.emobile.springtodo.model.dto.TodoDto;
import com.emobile.springtodo.model.dto.TodoUpdateDto;
import com.emobile.springtodo.model.entity.Todo;
import com.emobile.springtodo.model.mapper.TodoMapper;
import com.emobile.springtodo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "todos")
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    @Cacheable(key = "#id")
    public TodoDto findTodo(Long id) {
        return todoMapper.toDto(todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo with id:" + id + " was not found.")));
    }

    @Override
    public List<TodoDto> findAllTodo() {
        return todoRepository.findAll().stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(key = "#result.id")
    public TodoDto saveTodo(TodoCreateDto incoming) {
        return todoMapper.toDto(
                todoRepository.save(
                        todoMapper.toEntity(incoming)));
    }

    @Override
    @CacheEvict(key = "#id")
    public TodoDto updateTodo(TodoUpdateDto incoming, Long id) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo with id:" + id + " was not found."));
        todoMapper.updateEntityFromDto(incoming, existingTodo);
        Todo updatedDto = todoRepository.update(existingTodo, id);
        return todoMapper.toDto(updatedDto);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "#id"),
            @CacheEvict(key = "'list'")
    })
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo with id:" + id + " was not found.");
        }
        todoRepository.deleteById(id);
    }

    @Override
    @CachePut(key = "#id")
    public TodoDto completeById(Long id) {
        return todoMapper.toDto(todoRepository.completeById(id));
    }

    @Override
    @CachePut(key = "#id")
    public TodoDto incompleteById(Long id) {
        return todoMapper.toDto(todoRepository.incompleteById(id));
    }

    @Override
    public boolean existsById(Long id) {
        return todoRepository.existsById(id);
    }
}
