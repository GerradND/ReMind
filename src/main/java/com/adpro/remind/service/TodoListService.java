package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import org.springframework.stereotype.Component;

@Component
public interface TodoListService {
    TodoList addTodoList(TodoList todoList);
    TodoList addTodoItem(int idList, TodoItem todoItem);
    TodoList deleteTodoList(int id);
    TodoItem deleteTodoItem(int idList, int idItem);
    TodoList showTodoList(int id);
    Iterable<TodoList> showAllTodoList(Guild guild);
}
