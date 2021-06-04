package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.TodoItemRepository;
import com.adpro.remind.repository.TodoListRepository;
import com.adpro.remind.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class TodoListServiceImpl implements TodoListService{
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TodoItemRepository todoItemRepository;
    @Autowired
    private GuildRepository guildRepository;

    @Override
    public void addTodoList(TodoList todoList){

        todoListRepository.save(todoList);
    }

    @Override
    public void addTodoItem(int idList, TodoItem todoItem){
        TodoList todoList = todoListRepository.findById(idList);
        todoItem.setTodoList(todoList);
        todoItemRepository.save(todoItem);
    }

    @Override
    public TodoList deleteTodoList(int id){
        TodoList todoList = todoListRepository.findById(id);
        if(todoList == null) return null;
        todoListRepository.deleteById(id);
        return todoList;
    }

    @Override
    public TodoItem deleteTodoItem(int idList, int idItem){
        TodoItem todoItem = todoItemRepository.findById(idItem);
        if(todoItem == null) return null;
        todoItemRepository.deleteById(idItem);
        return todoItem;
    }

    @Override
    public TodoList showTodoList(int id){
        return todoListRepository.findById(id);
    }

    @Override
    public Iterable<TodoList> showAllTodoList(Guild guild) {
        if (guild == null){
            return null;
        }
        return todoListRepository.findByGuild(guild);
    }
}