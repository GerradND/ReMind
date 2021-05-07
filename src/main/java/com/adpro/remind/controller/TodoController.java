package com.adpro.remind.controller;

import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import com.adpro.remind.service.TodoListServiceImpl;
import com.austinv11.servicer.Service;
import com.sun.tools.javac.comp.Todo;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class TodoController {
    @Autowired
    private TodoListServiceImpl todoListService;
    private static final String addTodoList = "^-list\\s+ADD";
    private static final String addTodoItem = "^-list\\s+ADDITEM";
    private static final String deleteTodoList = "^-list\\s+DELETE";
    private static final String deleteTodoItem = "^-list\\s+DELETE";
    private static final String showTodoList = "^-list\\s+SHOW";
    private static final String showAllTodoList = "^-list\\s+SHOW\\s+ALL";

    public void mainHandler(MessageCreateEvent event){
        System.out.println(event.getMessage());
        System.out.println(todoListService.getClass());
//        addTodoList(event);
        System.out.println(deleteTodoList(event));
    }

    public void addTodoList(MessageCreateEvent event){
        TodoList todoList = new TodoList(event.getMessage().getContent());
        todoListService.addTodoList(todoList);
    }

    public void addTodoItem(MessageCreateEvent event){
        TodoItem todoItem = new TodoItem(event.getMessage().getContent());
        todoListService.addTodoItem(todoItem);
    }

    public boolean deleteTodoList(MessageCreateEvent event){
        return todoListService.deleteTodoList(0);
    }

    public boolean deleteTodoItem(MessageCreateEvent event){
        return todoListService.deleteTodoItem(0);
    }

    public TodoList showTodoList(MessageCreateEvent event){
        return todoListService.showTodoList(0);
    }

    public Iterable<TodoList> showAllTodoList(MessageCreateEvent event){
        return todoListService.showAllTodoList();
    }

}
