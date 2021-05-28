package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderDetailCommandTests {
    @Mock
    Message message;

    @Mock
    TaskService taskService;

    @Mock
    Guild guild;

    @InjectMocks
    ReminderDetailCommand reminderDetailCommand;

    private Task task;

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.of(2021,05,28);
        LocalTime time = LocalTime.of(12,00);
        task = new Task("Adpro", date, time, guild);
        task.setIdTask(1);
    }

    @Test
    void testReminderDetailOutput(){
        String[] inputContent = {"-reminder", "detail", "1"};
        when(taskService.detailTask(1)).thenReturn(task);
        MessageEmbed messageOutput = reminderDetailCommand.getOutputMessage(message, inputContent);
        Assertions.assertEquals("Detail Tugas [#1]", messageOutput.getTitle());
    }
}
