package com.emobile.springtodo.model;

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
    //Возможно будет проблема в нейминге: в бд due_date
    private LocalDateTime dueDate;
    private boolean completed;
}
