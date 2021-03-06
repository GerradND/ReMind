package com.adpro.remind.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.TodoItemRepository;
import com.adpro.remind.repository.TodoListRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TodoListServiceImplTest {
    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TodoItemRepository todoItemRepository;

    @Mock
    private GuildRepository guildRepository;

    @InjectMocks
    private TodoListServiceImpl todoListService;

    private TodoList todoList;
    private TodoItem todoItem;
    private Guild guild;

    @BeforeEach
    public void setUp() {
        guild = new Guild("1");
        todoList = new TodoList("Do Something", guild);
        todoItem = new TodoItem("An item");
    }

    @Test
    void testServiceAddTodoList() {
        lenient().when(guildRepository.findByIdGuild("1")).thenReturn(guild);
        lenient().when(todoListRepository.save(todoList)).thenReturn(todoList);
        TodoList addedTodoList =  todoListService.addTodoList(todoList);
        assertEquals(todoList, addedTodoList);
    }

    @Test
    void testServiceAddTodoItem() {
        todoList.getTodoItemSet().add(todoItem);
        lenient().when(guildRepository.findByIdGuild("1")).thenReturn(guild);
        lenient().when(todoItemRepository.save(todoItem)).thenReturn(todoItem);
        lenient().when(todoListRepository.findById(todoList.getId())).thenReturn(todoList);
        TodoList updatedTodoList = todoListService.addTodoItem(todoList.getId(), todoItem);
        assertEquals(todoItem, updatedTodoList.getTodoItemSet().get(0));
    }

    @Test
    void testServiceShowTodoList() {
        lenient().when(todoListRepository.findById(todoList.getId())).thenReturn(todoList);
        TodoList retrievedTodoList = todoListService.showTodoList(todoList.getId());
        assertEquals(todoList, retrievedTodoList);
    }

    @Test
    void testServiceShowAllTodoList() {
        Iterable<TodoList> todoLists = new ArrayList<>();
        lenient().when(todoListRepository.findByGuild(guild)).thenReturn(todoLists);
        Iterable<TodoList> retrievedTodoLists = todoListService.showAllTodoList(guild);
        assertEquals(todoLists, retrievedTodoLists);
    }

    @Test
    void testServiceDeleteTodoList() {
        todoListService.deleteTodoList(todoList.getId());
        assertNull(todoListService.showTodoList(todoList.getId()));
    }

    @Test
    void testServiceDeleteTodoItem() {
        todoListService.deleteTodoItem(todoList.getId(), todoItem.getId());
        assertNull(todoListService.showTodoList(todoList.getId()));
    }

}