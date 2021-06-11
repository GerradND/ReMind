package com.adpro.remind.command.list;

import com.adpro.remind.model.Guild;
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
public class ListAddTodoListCommandTest {
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
    private ListAddTodoListCommand listAddTodoListCommand;

    private Guild guild;
    private TodoList todoList;

    @BeforeEach
    public void setUp(){
        guild = new Guild("1");
        todoList = new TodoList("Monday", guild);
    }

    @Test
    public void testAddTodoList(){
        String[] inputContent = {"-list", "add", "Monday"};
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("1");
        lenient().when(todoListService.addTodoList(any(TodoList.class))).thenReturn(todoList);
        lenient().when(message.reply(any(MessageEmbed.
                class))).thenReturn(messageAction);
        listAddTodoListCommand.getOutputMessage(message, inputContent);
    }

}