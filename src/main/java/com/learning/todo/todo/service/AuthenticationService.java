package com.learning.todo.todo.service;

import com.learning.todo.todo.security.CustomAuthenticationToken;
import com.learning.todo.todo.dto.AuthLoginDto;
import com.learning.todo.todo.dto.AuthRegisterDto;
import com.learning.todo.todo.entity.Role;
import com.learning.todo.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;

    @Autowired
    public AuthenticationService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager manager
    ) {
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User signUp(AuthRegisterDto registerDto) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(this.userService.findRoleById(registerDto.getRoleId()));
        return this.userService.createUser(
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                roles
        );
    }

    public User authenticate(AuthLoginDto loginDto) {
        this.manager.authenticate(
                new CustomAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword(),
                        null
                )
        );

        return userService.findByUsername(loginDto.getUsername());
    }
}
