package com.learning.todo.todo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @Column(name="name", nullable=false, length=255, unique=true)
    public String name;

    @ManyToMany(mappedBy = "roles")
    public Set<User> users;

}
