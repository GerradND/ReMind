package com.adpro.remind.service;

import com.adpro.remind.model.TodoList;

public interface TodoListService {
    void addTodoList(TodoList todoList);
    boolean deleteTodoList(int id);
    Iterable<TodoList> showAllTodoList();
}
