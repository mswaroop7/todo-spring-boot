package com.learning.todo.todo.controllers;

import com.learning.todo.todo.dto.AuthLoginDto;
import com.learning.todo.todo.dto.AuthRegisterDto;
import com.learning.todo.todo.dto.LoginResponse;
import com.learning.todo.todo.entity.User;
import com.learning.todo.todo.service.AuthenticationService;
import com.learning.todo.todo.service.JwtService;
import com.learning.todo.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(
            JwtService jwtService,
            AuthenticationService authenticationService,
            UserService userService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody AuthRegisterDto registerUserDto) {
        User registeredUser = authenticationService.signUp(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody AuthLoginDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        UserDetails userDetails = userService.loadUserByUsername(authenticatedUser.getUsername());
        HashMap<String, Object> extras = new HashMap<>();
        extras.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String jwtToken = jwtService.generateToken(extras, userDetails);

        LoginResponse loginResponse = LoginResponse
                .builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExp())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
