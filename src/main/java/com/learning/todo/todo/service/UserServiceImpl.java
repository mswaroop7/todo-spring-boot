package com.learning.todo.todo.service;

import com.learning.todo.todo.dao.RoleRepository;
import com.learning.todo.todo.dao.UserRepository;
import com.learning.todo.todo.entity.Role;
import com.learning.todo.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                this.mapRoles(user.getUsername())
        );
    }

    public List<String> findRoleNamesByUsername(String username) {
        return userRepository.findRoleNamesByUsername(username);
    }

    private List<GrantedAuthority> mapRoles(String username) {
        return userRepository.findRoleNamesByUsername(username).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public User createUser(
            String firstName,
            String lastName,
            String username,
            String password,
            Set<Role> roles
    ) {
        User user = User.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .roles(roles)
                .build();
        userRepository.save(user);
        return user;
    }

    public Role findRoleById(@NonNull Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }
}
