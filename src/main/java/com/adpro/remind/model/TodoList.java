package com.adpro.remind.model;

import jdk.nashorn.internal.objects.annotations.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="todo_list")
@Data
@Getter
public class TodoList {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "todoList")
    private Set<TodoItem> todoItemSet;

    public TodoList(String title){
        this.id = id;
        this.title = title;
    }
}