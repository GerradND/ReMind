package com.adpro.remind.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="todo_item")
@Data
@Getter
@NoArgsConstructor
public class TodoItem {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(name="id_list")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TodoList todoList;

    public TodoItem(String name){
        this.id = id;
        this.name = name;
    }

}
