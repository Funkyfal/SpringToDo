package com.emobile.springtodo.service;

import com.emobile.springtodo.exception.TodoNotFoundException;
import com.emobile.springtodo.model.dto.*;
import com.emobile.springtodo.model.entity.Todo;
import com.emobile.springtodo.model.mapper.TodoMapper;
import com.emobile.springtodo.repository.TodoRepository;
import com.emobile.springtodo.service.implementation.TodoServiceImpl;
import io.micrometer.core.instrument.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TodoServiceImpl Unit Tests")
class TodoServiceImplTest {

    @Mock
    private TodoRepository repo;
    @Mock
    private TodoMapper mapper;
    @Mock
    private MeterRegistry registry;

    @InjectMocks
    private TodoServiceImpl service;

    private Counter counter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        counter = mock(Counter.class);
        when(registry.counter("todos.completed")).thenReturn(counter);
    }

    @Test
    @DisplayName("findTodo: should return DTO when exists")
    void findTodoFound() {
        Todo entity = new Todo(1L, "T", "D", LocalDateTime.now(), false);
        TodoDto dto = new TodoDto(1L, "T", "D", entity.getDue_date(), false);

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        TodoDto result = service.findTodo(1L);

        assertThat(result).isEqualTo(dto);
        verify(repo).findById(1L);
        verify(mapper).toDto(entity);
    }

    @Test
    @DisplayName("findTodo: should throw when not found")
    void findTodoNotFound() {
        when(repo.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findTodo(42L))
                .isInstanceOf(TodoNotFoundException.class)
                .hasMessageContaining("42");
    }

    @Test
    @DisplayName("completeById: should increment counter and return DTO")
    void completeByIdIncrementsCounter() {
        Todo entity = new Todo(2L, "X", null, LocalDateTime.now(), true);
        TodoDto dto = new TodoDto(2L, "X", null, entity.getDue_date(), true);

        when(repo.completeById(2L)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        TodoDto result = service.completeById(2L);

        verify(counter).increment();
        assertThat(result).isEqualTo(dto);
    }
}
