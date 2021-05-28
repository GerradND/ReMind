package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.GuildServiceImpl;
import com.adpro.remind.service.TaskService;
import jdk.vm.ci.meta.Local;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
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
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderAddCommandTests {
    @Mock
    Message message;

    @Mock
    net.dv8tion.jda.api.entities.Guild DiscordGuild;

    @InjectMocks
    ReminderAddCommand reminderAddCommand;

    @Mock
    private Task task;

    private Guild guild;

    @Mock
    private GuildService guildService;

    @Mock
    private TaskService taskService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        guild = new Guild("814323773107994655");
    }

    @Test
    void testReminderAddNewTask(){
        String[] inputContent = {"-reminder", "add", "Adpro", "28/05/2021", "12:00"};
        Task task = reminderAddCommand.newTask(guild, inputContent);
        Assertions.assertEquals("Adpro", task.getName());
    }
/*
    @Test
    void testReminderAddOutput(){
        String[] inputContent = {"-reminder", "add", "Adpro", "28/05/2021", "12:00"};
        String idGuild = guild.getIdGuild();
        when(message.getGuild()).thenReturn(DiscordGuild);
        when(DiscordGuild.getId()).thenReturn(idGuild);
        when(guildService.getGuildByID(idGuild)).thenReturn(guild);

//        LocalDate date = LocalDate.of(2021,05,28);
//        LocalTime time = LocalTime.of(12,00);
//        Task task = new Task("Adpro", date, time, guild);
//        when(reminderAddCommand.newTask(guild, inputContent)).thenReturn(task);
//        task.setIdTask(1);

        when(task.getIdTask()).thenReturn(1);

        MessageEmbed output = reminderAddCommand.getOutputMessage(message, inputContent);
        Assertions.assertEquals(output.getTitle(), "Tugas berhasil dibuat!");
    }

 */
}
