package com.learning.todo.todo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;
}
