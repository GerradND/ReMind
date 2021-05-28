package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Task task = reminderAddCommand.newTask(guild.getIdGuild(), inputContent);
        Assertions.assertEquals("Adpro", task.getName());
    }
}
