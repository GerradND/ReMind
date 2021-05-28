package com.adpro.remind.command.reminder;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.TaskRepository;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
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
public class ReminderUpdateCommandTests {
    @Mock
    private Message message;

    @Mock
    private Guild guild;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ReminderAddCommand reminderAddCommand;

    @InjectMocks
    private ReminderUpdateCommand reminderUpdateCommand;

    private Task oldTask;

    @BeforeEach
    public void setUp(){
        String[] inputContent = {"-reminder", "add", "Adpro", "29/05/2021", "20:00"};
        LocalDate date = LocalDate.of(2021, 05, 31);
        LocalTime time = LocalTime.of(19,00);
        oldTask = new Task("Adpro", date, time, guild);
        oldTask.setIdTask(1);


    }

    @Test
    void testReminderUpdateOutput(){
        String[] inputContent = {"-reminder", "update", "1", "29/05/2021", "20:00"};

        LocalDate date = LocalDate.of(2021, 05, 29);
        LocalTime time = LocalTime.of(20,00);
        Task newTask = new Task("Adpro", date, time, guild);
        newTask.setIdTask(1);

        when(taskService.updateTask(1, date, time)).thenReturn(newTask);
        reminderUpdateCommand.getOutputMessage(message, inputContent);
    }
}
