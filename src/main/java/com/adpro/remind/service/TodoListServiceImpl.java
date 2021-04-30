package com.adpro.remind.service;

import com.adpro.remind.repository.TodoListRepository;
import com.adpro.remind.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoListServiceImpl implements TodoListService{
    @Autowired
    private TodoListRepository todoListRepository;

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
    public Iterable<TodoList> showAllTodoList() {
        return todoListRepository.findAll();
    }
}