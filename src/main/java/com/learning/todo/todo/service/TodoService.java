package com.learning.todo.todo.service;


import com.learning.todo.todo.entity.Todo;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TodoService {
    public Todo create(Todo todo);
    public List<Todo> findAllByUserId(@NonNull Integer userId);
    public Todo findById(@NonNull Integer id);
    public Todo findByIdAndUserId(@NonNull Integer id, @NonNull Integer userId);
}
