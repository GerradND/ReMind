package com.adpro.remind.command.list;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListServiceImpl;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListAddTodoItemCommandTest {
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
    private ListAddTodoItemCommand listAddTodoItemCommand;

    private Guild guild;
    private TodoList todoList;
    private TodoItem todoItem;

    @BeforeEach
    public void setUp(){
        guild = new Guild("1");
        todoList = new TodoList("Monday", guild);
        todoItem = new TodoItem("belajar");
        todoList.getTodoItemSet().add(todoItem);
    }

    @Test
    public void testAddTodoItem(){
        String[] inputContent = {"-list", "additem", "1", "belajar"};
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("1");
        lenient().when(todoListService.addTodoItem(any(Integer.class),any(TodoItem.class))).thenReturn(todoList);
        lenient().when(message.reply(any(MessageEmbed.class))).thenReturn(messageAction);
        listAddTodoItemCommand.getOutputMessage(message, inputContent);
        verify(todoListService, times(1)).addTodoItem(1, todoItem);
    }
}
