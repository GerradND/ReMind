package com.adpro.remind.service;

import com.adpro.remind.repository.TodoListRepository;
import com.adpro.remind.model.TodoList;

public class TodoListServiceImpl implements TodoListService{
    private TodoListRepository todoListRepository;

    @Override
    public void addTodoList(TodoList todoList){
        todoListRepository.save(todoList);
    }

    @Override
    public boolean deleteTodoList(int id){
        if(todoListRepository.findById(id) == null) return false;
        todoListRepository.deleteById(Integer.toString(id));
        return true;
    }

    @Override
    public Iterable<TodoList> showAllTodoList() {
        return todoListRepository.findAll();
    }
}