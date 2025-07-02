package com.emobile.springtodo.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.*;
import org.testcontainers.containers.*;
import org.testcontainers.junit.jupiter.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.skyscreamer.jsonassert.JSONAssert;
import org.testcontainers.junit.jupiter.Container;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("TodoController Integration Tests")
class TodoControllerIT {

    @Container
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("tododb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", pg::getJdbcUrl);
        registry.add("spring.datasource.username", pg::getUsername);
        registry.add("spring.datasource.password", pg::getPassword);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /todo returns list with pagination")
    @Sql(statements = {
            "INSERT INTO todo(title,description,due_date,completed) VALUES('A','a',now(),false)",
            "INSERT INTO todo(title,description,due_date,completed) VALUES('B','b',now(),true)"
    })
    void list_WithSqlData_ShouldReturnTwo() throws Exception {
        MvcResult res = mvc.perform(get("/todo")
                        .param("limit","10")
                        .param("offset","0"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = """
            [
              {"id":1,"title":"A","description":"a","completed":false},
              {"id":2,"title":"B","description":"b","completed":true}
            ]
            """;
        JSONAssert.assertEquals(expected, res.getResponse().getContentAsString(), false);
    }

    @Test
    @DisplayName("POST /todo then GET by id")
    void createAndGet() throws Exception {
        String now = LocalDateTime.now()
                .plusSeconds(1)
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        String body = """
            {"title":"T1","description":"desc","due_date":"%s","completed":false}
            """.formatted(now);

        MvcResult create = mvc.perform(post("/todo")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        String json = create.getResponse().getContentAsString();
        Integer idInt = JsonPath.read(json, "$.id");
        Long id = idInt.longValue();

        mvc.perform(get("/todo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("T1"));
    }
}
