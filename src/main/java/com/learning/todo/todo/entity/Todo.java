package com.learning.todo.todo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name="todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="title", length=50, nullable=false)
    private String title;

    @Column(name="description", nullable=true)
    private String description;

    @Column(name="completion_date", nullable=false)
    private Date completionDate;

    @ManyToOne
    @JoinColumn(name="created_by_id", nullable=false)
    private User createdBy;
}
