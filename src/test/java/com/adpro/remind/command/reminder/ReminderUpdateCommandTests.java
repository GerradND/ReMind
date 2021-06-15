package com.adpro.remind.command.reminder;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import java.time.LocalDate;
import java.time.LocalTime;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReminderUpdateCommandTests {
    @Mock
    private Message message;

    @Mock
    MessageChannel messageChannel;

    @Mock
    MessageAction messageAction;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private ReminderUpdateCommand reminderUpdateCommand;

    private Task oldTask;

    @BeforeEach
    public void setUp() {
        LocalDate date = LocalDate.of(2021, 5, 31);
        LocalTime time = LocalTime.of(19, 0);
        oldTask = new Task("Adpro", date, time);
        oldTask.setIdTask(1);
    }

    @Test
    public void testReminderUpdateOutput() {
        String[] inputContent = {"-reminder", "update", "1", "29/05/2021", "20:00"};

        LocalDate newDate = LocalDate.of(2021, 5, 29);
        LocalTime newTime = LocalTime.of(20, 0);
        Task newTask = oldTask;
        newTask.setDate(newDate);
        newTask.setTime(newTime);

        lenient().when(taskService.updateTask(1, newDate, newTime)).thenReturn(newTask);

        EmbedBuilder embedOutput = reminderUpdateCommand.getEmbedOutput(oldTask);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderUpdateCommand.getOutputMessage(message, inputContent);

        MessageEmbed output = reminderUpdateCommand.embedOutput.build();
        Assertions.assertEquals(":white_check_mark:  Tugas dengan ID: 1 berhasil diupdate.", output.getTitle());
    }

}
