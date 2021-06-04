package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "todoList")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TodoItem> todoItemSet = new ArrayList<>() ;

    @ManyToOne
    @JoinColumn(name = "id_guild")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Guild guild;

    public TodoList(String title, Guild guild){
        this.id = id;
        this.title = title;
        this.guild = guild;
    }
}
