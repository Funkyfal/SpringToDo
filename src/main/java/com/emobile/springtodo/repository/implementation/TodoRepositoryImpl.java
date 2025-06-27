package com.emobile.springtodo.repository.implementation;

import com.emobile.springtodo.model.entity.Todo;
import com.emobile.springtodo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Todo> todoRowMapper = (rs, rowNum) -> Todo.builder()
            .id(rs.getLong("id"))
            .title(rs.getString("title"))
            .description(rs.getString("description"))
            .completed(rs.getBoolean("completed"))
            .due_date(rs.getTimestamp("due_date").toLocalDateTime())
            .build();

    @Override
    public Optional<Todo> findById(Long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        try {
            Todo todo = jdbcTemplate.queryForObject(sql, todoRowMapper, id);
            return Optional.ofNullable(todo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Todo> findAll() {
        String sql = "SELECT * FROM todo";
        return jdbcTemplate.query(sql, todoRowMapper);
    }

    @Override
    public Todo save(Todo todo) {
        String sql = """
        INSERT INTO todo (title, description, due_date, completed)
        VALUES (?, ?, ?, ?)
    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setTimestamp(3, todo.getDue_date() != null
                    ? Timestamp.valueOf(todo.getDue_date())
                    : null);
            ps.setBoolean(4, todo.isCompleted());
            return ps;
        }, keyHolder);

        todo.setId(keyHolder.getKey().longValue());
        return todo;
    }


    @Override
    public Todo update(Todo todo, Long id) {
        String sql = """
                UPDATE todo  SET title = ?, description = ?, due_date = ?, completed = ? WHERE id = ?
                """;
        jdbcTemplate.update(sql,
                todo.getTitle(),
                todo.getDescription(),
                todo.getDue_date(),
                todo.isCompleted(),
                id);
        return todo;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Todo completeById(Long id) {
        String sql = "UPDATE todo SET completed = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return findById(id).orElseThrow();
    }

    @Override
    public Todo incompleteById(Long id) {
        String sql = "UPDATE todo SET completed = FALSE WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return findById(id).orElseThrow();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(1) FROM todo WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
