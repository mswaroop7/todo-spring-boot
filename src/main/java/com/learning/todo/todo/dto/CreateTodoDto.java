package com.learning.todo.todo.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class CreateTodoDto {
    private String title;
    private String description;
    private Date completionDate;
}
