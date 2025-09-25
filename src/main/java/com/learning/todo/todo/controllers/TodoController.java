package com.learning.todo.todo.controllers;


import com.learning.todo.todo.dto.CreateTodoDto;
import com.learning.todo.todo.dto.TodoDto;
import com.learning.todo.todo.entity.Todo;
import com.learning.todo.todo.entity.User;
import com.learning.todo.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<TodoDto> createTodo(@RequestBody CreateTodoDto createTodoDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Todo todo = Todo
                .builder()
                .title(createTodoDto.getTitle())
                .description(createTodoDto.getDescription())
                .completionDate(createTodoDto.getCompletionDate())
                .createdBy(user)
                .build();
        todo = this.todoService.create(todo);
        TodoDto todoDto = new TodoDto(todo);
        return ResponseEntity.ok(todoDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<TodoDto>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Todo> todos = todoService.findAllByUserId(user.getId());
        List<TodoDto> allTodos = todos.stream().map(TodoDto::new).toList();
        return ResponseEntity.ok(allTodos);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Integer todoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Todo todo = todoService.findByIdAndUserId(todoId, user.getId());
        return ResponseEntity.ok(new TodoDto(todo));
    }
}
