package com.adpro.remind.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="todo_item")
@Data
@Getter
public class TodoItem {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="id_list")
    private TodoList todoList;

    public TodoItem(String name){
        this.id = id;
        this.name = name;
    }

}
