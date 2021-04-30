package com.adpro.remind.model;

import jdk.nashorn.internal.objects.annotations.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column
    private String title;

}