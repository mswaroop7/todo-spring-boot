package com.learning.todo.todo.service;

import com.learning.todo.todo.constants.ErrorMessages;
import com.learning.todo.todo.dao.TodoRepository;
import com.learning.todo.todo.entity.Todo;
import com.learning.todo.todo.exceptions.TodoByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServicImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServicImpl(TodoRepository repository) {
        this.todoRepository = repository;
    }

    @Override
    public Todo create(@NonNull Todo todo) {
        this.todoRepository.save(todo);
        return todo;
    }

    @Override
    public List<Todo> findAllByUserId(@NonNull Integer userId) {
        return this.todoRepository.findAllByCreatedBy_IdOrderById(userId);
    }

    @Override
    public Todo findById(@NonNull Integer id) {
        return this.todoRepository.findById(id).get();
    }

    @Override
    public Todo findByIdAndUserId(@NonNull Integer id, @NonNull Integer userId) {
        Todo todo = this.todoRepository.findByIdAndCreatedBy_Id(id, userId);
        if (todo == null) {
            throw new TodoByIdNotFoundException(ErrorMessages.TODO_BY_ID_NOT_FOUND);
        }
        return todo;
    }

}
