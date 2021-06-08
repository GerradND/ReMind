package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Task;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderDetailCommandTests {
    @Mock
    private Message message;

    @Mock
    private TaskService taskService;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @InjectMocks
    private ReminderDetailCommand reminderDetailCommand;

    private Task task;

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.of(2021, 5,28);
        LocalTime time = LocalTime.of(12, 0);
        task = new Task("Adpro", date, time);
        task.setIdTask(1);
    }

    @Test
    void testReminderDetailOutput(){
        String[] inputContent = {"-reminder", "detail", "1"};
        when(taskService.detailTask(1)).thenReturn(task);

        EmbedBuilder embedOutput = reminderDetailCommand.getEmbedOutput(task);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderDetailCommand.getOutputMessage(message, inputContent);
        MessageEmbed output = reminderDetailCommand.embedOutput.build();

        Assertions.assertEquals("Detail Tugas [#1]", output.getTitle());
    }
}
