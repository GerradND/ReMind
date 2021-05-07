package com.adpro.remind.controller;

import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListServiceImpl;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.regex.Pattern;

@Controller
public class TodoController {
    @Autowired
    private TodoListServiceImpl todoListService;
    private GatewayDiscordClient client;
    private final Pattern addTodoListPattern = Pattern.compile("^-list\\s+ADD");
    private final Pattern addTodoItemPattern = Pattern.compile("^-list\\s+ADDITEM");
    private final Pattern deleteTodoListPattern = Pattern.compile("^-list\\s+DELETE");
    private final Pattern deleteTodoItemPattern = Pattern.compile("^-list\\s+DELETE");
    private final Pattern showTodoListPattern = Pattern.compile("^-list\\s+SHOW");
    private final Pattern showAllTodoListPattern = Pattern.compile("^-list\\s+SHOW\\s+ALL");

    public GatewayDiscordClient mainHandler(MessageCreateEvent event, GatewayDiscordClient client){
        if(this.client != null) this.client = client;
        String messageContent = event.getMessage().getContent();
        if(addTodoItemPattern.matcher(messageContent).find()){
            addTodoItem(event);
        }
        else if(deleteTodoItemPattern.matcher(messageContent).find()){
            deleteTodoItem(event);
        }
        else if(addTodoListPattern.matcher(messageContent).find()){
            addTodoList(event);
        }
        else if(deleteTodoListPattern.matcher(messageContent).find()){
            deleteTodoList(event);
        }
        else if(showTodoListPattern.matcher(messageContent).find()){
            showTodoList(event);
        }
        else if(showAllTodoListPattern.matcher(messageContent).find()){
            showAllTodoList(event);
        }
        return client;
    }

    public void addTodoList(MessageCreateEvent event){
        TodoList todoList = new TodoList(event.getMessage().getContent());
        todoListService.addTodoList(todoList);
    }

    public void addTodoItem(MessageCreateEvent event){
        TodoItem todoItem = new TodoItem(event.getMessage().getContent());
        event.getMessage().getChannel().block().createMessage("hiyaa").block();
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
