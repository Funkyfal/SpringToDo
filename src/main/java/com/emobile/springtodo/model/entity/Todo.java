package com.emobile.springtodo.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime due_date;
    private boolean completed;
}
