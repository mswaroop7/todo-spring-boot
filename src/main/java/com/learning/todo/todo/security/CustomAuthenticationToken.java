package com.learning.todo.todo.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;


public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private final Object principal;       // your custom user object
    @Getter
    private final Object credentials;     // password or token (optional)

    public CustomAuthenticationToken(Object principal, Object credentials, List<String> roles) {
        super(roles.stream().map(SimpleGrantedAuthority::new).toList()); // authorities can be null; we handle roles ourselves
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true); // mark as authenticated after validation
    }
}

