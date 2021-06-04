package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.ReminderRepository;
import com.adpro.remind.repository.TaskRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderSetCommandTests {
    @Mock
    private Message message;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ReminderRepository reminderRepository;

    @InjectMocks
    private ReminderSetCommand reminderSetCommand;

    private Task task;
    private Reminder reminder;
    private String channelID = "";

    @BeforeEach
    void setUp(){
        LocalDate date = LocalDate.of(2021,05,28);
        LocalTime time = LocalTime.of(12,00);
        task = new Task("Adpro", date, time);
        task.setIdTask(1);

        LocalDate newDate = LocalDate.of(2021,05,26);
        reminder = new Reminder(newDate, time, channelID);
    }

    @Test
    void testReminderSetOutput(){
        String[] inputContent = {"-reminder", "set", "1", "2", "hari"};

        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.getId()).thenReturn(channelID);

        when(taskService.findByIDTask(1)).thenReturn(task);
        lenient().when(taskRepository.save(any(Task.class))).thenReturn(task);
        lenient().when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);

        EmbedBuilder embedOutput = reminderSetCommand.getEmbedOutput("1", reminder);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderSetCommand.getOutputMessage(message, inputContent);
        MessageEmbed output = reminderSetCommand.embedOutput.build();
        Assertions.assertEquals("Reminder untuk tugas dengan ID 1 telah dipasang.", output.getTitle());
    }
}
