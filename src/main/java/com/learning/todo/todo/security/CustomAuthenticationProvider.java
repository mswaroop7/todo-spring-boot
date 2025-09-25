package com.learning.todo.todo.security;

import com.learning.todo.todo.entity.User;
import com.learning.todo.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String principal = authentication.getPrincipal().toString();
        String credentials = authentication.getCredentials().toString();

        // Your custom authentication logic
        // Example: check credentials against DB
        boolean valid = checkUser(principal, credentials);

        if (!valid) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Fetch custom roles from DB
        List<String> roles = userService.findRoleNamesByUsername(principal);

        // Return custom authentication token
        return new CustomAuthenticationToken(principal, null, roles);
    }

    private boolean checkUser(String principal, String credentials) {
        User user = userService.findByUsername(principal);

        if (user == null) {
            throw new BadCredentialsException("Username or password is not correct");
        }

        if(passwordEncoder.matches(credentials, user.getPassword())) {
            return true;
        }
        throw new BadCredentialsException("Username or password is not correct");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
