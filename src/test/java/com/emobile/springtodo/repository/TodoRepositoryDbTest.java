package com.emobile.springtodo.repository;

import com.emobile.springtodo.model.entity.Todo;
import com.emobile.springtodo.repository.implementation.TodoRepositoryImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import({TodoRepositoryImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("TodoRepositoryImpl DB Tests")
class TodoRepositoryDbTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    TodoRepository repo;

    @Test
    @DisplayName("Schema applied: table todo exists")
    void tableExists() {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name='todo'",
                Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Save and findById")
    void saveAndFind() {
        Todo t = new Todo(null, "X", "Y", LocalDateTime.now().plusSeconds(1), false);
        Todo saved = repo.save(t);
        assertThat(saved.getId()).isNotNull();

        Todo found = repo.findById(saved.getId()).orElseThrow();
        assertThat(found.getTitle()).isEqualTo("X");
    }
}
