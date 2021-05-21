package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.MessageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import net.dv8tion.jda.api.entities.Message;

@ExtendWith(MockitoExtension.class)
public class ReminderCommandTest {
    @Mock
    Message message;

    private TaskService taskService;
    private Task task;

    private ReminderAddCommand reminderAddCommand;

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.of(2021, 05, 31);
        LocalTime time = LocalTime.of(23, 55);
        task = new Task("Adpro", date, time);
    }

    @Test
    void testAddCommandOutput(){
        String[] inputContent = {"reminder", "add", "Tugas Adpro", "21/05/2021", "14:20"};
        reminderAddCommand.getOutputMessage(message, inputContent);
    }
}
