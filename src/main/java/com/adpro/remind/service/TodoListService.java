package com.adpro.remind.service;

import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import org.springframework.stereotype.Component;

@Component
public interface TodoListService {
    void addTodoList(TodoList todoList);
    void addTodoItem(TodoItem todoItem);
    boolean deleteTodoItem(int id);
    boolean deleteTodoList(int id);
    TodoList showTodoList(int id);
    Iterable<TodoList> showAllTodoList();
}
