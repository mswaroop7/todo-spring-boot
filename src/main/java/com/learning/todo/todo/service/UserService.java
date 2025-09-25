package com.learning.todo.todo.service;

import com.learning.todo.todo.entity.Role;
import com.learning.todo.todo.entity.User;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User createUser(
            String firstName,
            String lastName,
            String username,
            String password,
            Set<Role> roles
    );
    Role findRoleById(@NonNull Integer id);
    List<String> findRoleNamesByUsername(String username);
}
