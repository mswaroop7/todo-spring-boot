package com.learning.todo.todo.dto;

import com.learning.todo.todo.entity.Todo;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class TodoDto {
    private Integer id;
    private String title;
    private String description;
    private Date completionDate;
    private String createdBy;
    private Integer createdById;

    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.completionDate = todo.getCompletionDate();
        this.createdBy = todo.getCreatedBy().getFirstName() + " " + todo.getCreatedBy().getLastName();
        this.createdById = todo.getCreatedBy().getId();
    }
}
