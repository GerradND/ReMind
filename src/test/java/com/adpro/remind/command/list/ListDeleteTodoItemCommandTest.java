package com.adpro.remind.command.list;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListServiceImpl;
import com.sun.tools.javac.comp.Todo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ListDeleteTodoItemCommandTest {
    @Mock
    private net.dv8tion.jda.api.entities.Guild guildDC;

    @Mock
    private Message message;

    @Mock
    private EmbedBuilder eb;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @Mock
    private TodoListServiceImpl todoListService;

    @InjectMocks
    private ListDeleteTodoItemCommand listDeleteTodoItemCommand;

    private Guild guild;
    private TodoList todoList;
    private TodoItem todoItem;

    @BeforeEach
    public void setUp(){
        guild = new Guild("1");
        todoList = new TodoList("Monday", guild);
        todoItem = new TodoItem("Belajar");
        todoItem.setTodoList(todoList);
    }

    @Test
    public void testDeleteTodoItem(){
        String[] inputContent = {"-list", "deleteitem", "1", "1"};
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("1");
        lenient().when(todoListService.deleteTodoItem(any(Integer.class), any(Integer.class))).thenReturn(todoItem);
        lenient().when(message.reply(any(String.
                class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();
        listDeleteTodoItemCommand.getOutputMessage(message, inputContent);
        verify(todoListService, times(1)).deleteTodoItem(1,1);
    }

}
