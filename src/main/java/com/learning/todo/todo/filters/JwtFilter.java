package com.learning.todo.todo.filters;

import com.learning.todo.todo.security.CustomAuthenticationToken;
import com.learning.todo.todo.entity.User;
import com.learning.todo.todo.service.JwtService;
import com.learning.todo.todo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtFilter(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
            JwtService jwtService,
            UserService userService
    ) {
        this.resolver = resolver;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = header.substring(7);
            String user = jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (user != null && authentication == null) {
                User userDetails = userService.findByUsername(user);

                if (jwtService.isValid(jwt, userDetails)) {

                    CustomAuthenticationToken token = new CustomAuthenticationToken(
                            userDetails,
                            null,
                            userService.findRoleNamesByUsername(user)
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
           resolver.resolveException(request, response, null, e);
        }
    }
}
