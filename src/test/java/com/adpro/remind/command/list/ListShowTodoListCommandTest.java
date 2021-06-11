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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ListShowTodoListCommandTest {
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
    private ListShowTodoListCommand listShowTodoListCommand;

    private Guild guild;
    private TodoList todoList;
    private TodoItem todoItem;

    @BeforeEach
    public void setUp(){
        guild = new Guild("1");
        todoList = new TodoList("Monday", guild);
        todoItem = new TodoItem("Belajar");
        todoList.getTodoItemSet().add(todoItem);
        todoItem.setTodoList(todoList);
    }

    @Test
    public void testShowTodoList(){
        String[] inputContent = {"-list", "show", "1"};
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("1");
        lenient().when(todoListService.showTodoList(any(Integer.class))).thenReturn(todoList);
        lenient().when(message.reply(any(MessageEmbed.
                class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();
        listShowTodoListCommand.getOutputMessage(message, inputContent);
        verify(todoListService, times(1)).showTodoList(1);
    }

}
