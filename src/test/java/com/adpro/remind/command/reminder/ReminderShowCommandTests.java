package com.adpro.remind.command.reminder;

import static org.mockito.Mockito.when;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReminderShowCommandTests {
    @Mock
    private Message message;

    @Mock
    private net.dv8tion.jda.api.entities.Guild discordGuild;

    @Mock
    private TaskService taskService;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @InjectMocks
    private ReminderShowCommand reminderShowCommand;

    private Task task;
    private Guild guild;

    @BeforeEach
    public void setUp() {
        guild = new Guild("1234567890");
        LocalDate date = LocalDate.of(2021, 5,28);
        LocalTime time = LocalTime.of(12, 0);
        task = new Task("Adpro", date, time);
        task.setIdTask(1);
    }

    @Test
    void testReminderShowAllOutput() {
        String[] inputContent = {"-reminder", "show", "all"};

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        String idGuild = guild.getIdGuild();
        when(taskService.showAllTask(idGuild)).thenReturn(tasks);
        when(message.getGuild()).thenReturn(discordGuild);
        when(discordGuild.getId()).thenReturn(idGuild);

        EmbedBuilder embedOutput = reminderShowCommand.getEmbedOutput(tasks);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderShowCommand.getOutputMessage(message, inputContent);
        MessageEmbed output = reminderShowCommand.embedOutput.build();
        Field firstTaskField1 = output.getFields().get(0);
        Assertions.assertEquals(":id:", firstTaskField1.getName());
        Assertions.assertEquals("1", firstTaskField1.getValue());
    }

    @Test
    void testReminderShowDateOutput() {
        String[] inputContent = {"-reminder", "show", "28/05/2021"};

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        String idGuild = guild.getIdGuild();
        when(taskService.showTaskAtDate(task.getDate(), idGuild)).thenReturn(tasks);
        when(message.getGuild()).thenReturn(discordGuild);
        when(discordGuild.getId()).thenReturn(idGuild);

        EmbedBuilder embedOutput = reminderShowCommand.getEmbedOutput(tasks);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderShowCommand.getOutputMessage(message, inputContent);
        MessageEmbed output = reminderShowCommand.embedOutput.build();
        Field firstTaskField1 = output.getFields().get(0);
        Assertions.assertEquals(":id:", firstTaskField1.getName());
        Assertions.assertEquals("1", firstTaskField1.getValue());
    }
}
