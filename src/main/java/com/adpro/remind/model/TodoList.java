package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="todo_list")
@Data
@Getter
@NoArgsConstructor
public class TodoList {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "todoList", fetch = FetchType.LAZY)
    private Set<TodoItem> todoItemSet;

    @ManyToOne
    @JoinColumn(name = "id_guild")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Guild guild;

    public TodoList(String title){
        this.id = id;
        this.title = title;
    }
}
