package com.learning.todo.todo.dao;

import com.learning.todo.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    public List<Todo> findAllByCreatedBy_IdOrderById(@NonNull Integer id);
    public Todo findByIdAndCreatedBy_Id(@NonNull Integer id, @NonNull Integer userId);
}
