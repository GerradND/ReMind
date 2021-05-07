package com.adpro.remind.service;

import com.adpro.remind.model.TodoItem;
import com.adpro.remind.repository.TodoItemRepository;
import com.adpro.remind.repository.TodoListRepository;
import com.adpro.remind.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class TodoListServiceImpl implements TodoListService{
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TodoItemRepository todoItemRepository;

    @Override
    public void addTodoList(TodoList todoList){
        todoListRepository.save(todoList);
    }

    @Override
    public boolean deleteTodoList(int id){
        if(todoListRepository.findById(id) == null) return false;
        todoListRepository.deleteById(id);
        return true;
    }

    @Override
    public void addTodoItem(TodoItem todoItem){
        todoItemRepository.save(todoItem);
    }

    @Override
    public boolean deleteTodoItem(int id){
        if(todoItemRepository.findById(id) == null) return false;
        todoItemRepository.deleteById(id);
        return true;
    }

    @Override
    public TodoList showTodoList(int id){
        return todoListRepository.findById(id);
    }

    @Override
    public Iterable<TodoList> showAllTodoList() {
        return todoListRepository.findAll();
    }
}