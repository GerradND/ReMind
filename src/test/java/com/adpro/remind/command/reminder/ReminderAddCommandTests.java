package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.TaskRepository;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderAddCommandTests {
    @Mock
    Message message;

    @Mock
    MessageChannel messageChannel;

    @Mock
    MessageAction messageAction;

    @Mock
    net.dv8tion.jda.api.entities.Guild DiscordGuild;

    @InjectMocks
    ReminderAddCommand reminderAddCommand;

    @Mock
    private Task task;

    private Guild guild;

    @Mock
    private TaskService taskService;

    @Mock
    private GuildRepository guildRepository;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        guild = new Guild("1234567890");

        LocalDate date = LocalDate.of(2021,05,28);
        LocalTime time = LocalTime.of(12,00);
        task = new Task("Adpro", date, time);
    }

    @Test
    void testReminderAddOutput(){
        String[] inputContent = {"-reminder", "add", "Adpro", "28/05/2021", "12:00"};

        when(message.getGuild()).thenReturn(DiscordGuild);
        when(DiscordGuild.getId()).thenReturn("1234567890");

        lenient().when(guildRepository.findByIdGuild(guild.getIdGuild())).thenReturn(guild);

        lenient().when(guildRepository.save(any(Guild.class))).thenReturn(guild);
        lenient().when(taskRepository.save(any(Task.class))).thenReturn(task);

        when(taskService.createTask(any(Task.class), eq("1234567890"))).thenReturn(task);
        task.setIdTask(1);

        EmbedBuilder embedOutput = reminderAddCommand.getEmbedOutput(task);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderAddCommand.getOutputMessage(message, inputContent);

        MessageEmbed output = reminderAddCommand.embedOutput.build();
        Assertions.assertEquals("Tugas berhasil dibuat!", output.getTitle());

    }
}
