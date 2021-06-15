package com.adpro.remind.command.list;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListServiceImpl;
import java.util.ArrayList;
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

@ExtendWith(MockitoExtension.class)
public class ListShowAllTodoListCommandTest {
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
    private ListShowAllTodoListCommand listShowAllTodoListCommand;

    private Guild guild;
    private TodoList todoList1;
    private TodoList todoList2;
    private ArrayList<TodoList> todoLists;

    @BeforeEach
    public void setUp() {
        guild = new Guild("1");
        todoList1 = new TodoList("Monday", guild);
        todoList2 = new TodoList("Tuesday", guild);
        todoLists = new ArrayList<>();
        todoLists.add(todoList1);
        todoLists.add(todoList2);
    }

    @Test
    public void testShowAllTodoList() {
        String[] inputContent = {"-list", "showall"};
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("1");
        lenient().when(todoListService.showAllTodoList(any(Guild.class))).thenReturn(todoLists);
        lenient().when(message.reply(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();
        listShowAllTodoListCommand.getOutputMessage(message, inputContent);
    }
}
