package com.learning.todo.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AuthRegisterDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int roleId;
}
