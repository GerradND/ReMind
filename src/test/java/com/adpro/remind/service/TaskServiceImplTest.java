package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ReminderRepository;
import com.adpro.remind.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    GuildRepository guildRepository;

    @Mock
    ReminderRepository reminderRepository;

    @InjectMocks
    TaskServiceImpl taskServiceImpl;

    private Task task;
    private Guild guild;

    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.of(2021, 05, 31);
        LocalTime time = LocalTime.of(23, 55);

        guild = new Guild("814323773107994655");
        task = new Task("Adpro", date, time);
    }

    @Test
    void testServiceCreateTask(){
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        String idGuild = guild.getIdGuild();
        when(guildRepository.findByIdGuild(idGuild)).thenReturn(guild);

        Task createdTask = taskServiceImpl.createTask(task, guild.getIdGuild());
        Assertions.assertEquals(createdTask.getName(), task.getName());
    }

    @Test
    void testServiceDeleteTask(){
        Integer idTask = task.getIdTask();
        taskRepository.save(task);

        taskServiceImpl.deleteTask(idTask);
        Task foundTask = taskRepository.findByIdTask(idTask);
        Assertions.assertNull(foundTask);
    }

    @Test
    void testServiceUpdateTask(){
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        String idGuild = guild.getIdGuild();
        when(guildRepository.findByIdGuild(idGuild)).thenReturn(guild);

        LocalDate oldDate = task.getDate();
        Task savedTask = taskServiceImpl.createTask(task, idGuild);

        Integer idTask = savedTask.getIdTask();
        LocalDate newDate = LocalDate.of(2021, 06, 05);
        LocalTime oldTime = savedTask.getTime();

        when(taskRepository.findByIdTask(idTask)).thenReturn(savedTask);
        Task updatedTask = taskServiceImpl.updateTask(idTask, newDate, oldTime);

        Assertions.assertNotEquals(oldDate, updatedTask.getDate());
        Assertions.assertEquals(newDate, updatedTask.getDate());
    }

    @Test
    void testServiceShowAllTask(){
        List<Task> listTasks = new ArrayList<>();
        listTasks.add(task);

        String idGuild = guild.getIdGuild();
        when(guildRepository.findByIdGuild(idGuild)).thenReturn(guild);
        when(taskRepository.findByGuild(guild)).thenReturn(listTasks);

        Iterable<Task> tasks = taskServiceImpl.showAllTask(idGuild);

        List<Task> returnedTasks = (List<Task>) tasks;
        Assertions.assertEquals(listTasks, returnedTasks);
    }

    @Test
    void testServiceShowAtDate(){
        List<Task> listTasks = new ArrayList<Task>();
        listTasks.add(task);

        LocalDate date = task.getDate();
        String idGuild = guild.getIdGuild();

        when(guildRepository.findByIdGuild(idGuild)).thenReturn(guild);
        when(taskRepository.findByDateAndGuild(date, guild)).thenReturn(listTasks);

        Iterable<Task> tasks = taskServiceImpl.showTaskAtDate(date, idGuild);

        List<Task> returnedTasks = (List<Task>) tasks;
        Assertions.assertEquals(listTasks, returnedTasks);
    }

    @Test
    void testServiceDetailTask(){
        Integer idTask = task.getIdTask();
        when(taskRepository.findByIdTask(idTask)).thenReturn(task);
        Task returnedTask = taskServiceImpl.detailTask(idTask);

        Assertions.assertEquals(task, returnedTask);
    }

    @Test
    void testServiceFindByIDTask(){
        Integer idTask = task.getIdTask();
        when(taskRepository.findByIdTask(idTask)).thenReturn(task);
        Task returnedTask = taskServiceImpl.findByIDTask(idTask);

        Assertions.assertEquals(task.getName(), returnedTask.getName());
    }

    @Test
    void testServiceSetReminder(){
        LocalDate dateReminder = LocalDate.of(2021, 05, 29);
        LocalTime timeReminder = LocalTime.of(20, 15);
        String randomIDChannel = "814323773696114690";
        Reminder reminder = new Reminder(dateReminder, timeReminder, randomIDChannel);

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);

        taskServiceImpl.setReminder(reminder, task);
        Assertions.assertEquals(task.getReminders().size(), 1);

    }

}
