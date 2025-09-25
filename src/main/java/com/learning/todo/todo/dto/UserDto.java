package com.learning.todo.todo.dto;


import com.learning.todo.todo.entity.Role;
import com.learning.todo.todo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;

    public UserDto(User user, List<String> roles) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = roles;
    }
}
