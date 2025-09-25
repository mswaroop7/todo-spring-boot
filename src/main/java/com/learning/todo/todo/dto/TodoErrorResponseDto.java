package com.learning.todo.todo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class TodoErrorResponseDto {
    private int status;
    private String message;
    private long timestamp;
}
