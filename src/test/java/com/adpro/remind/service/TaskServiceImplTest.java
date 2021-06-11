package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ReminderRepository;
import com.adpro.remind.repository.TaskRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private Reminder reminder;

    @BeforeEach
    public void setUp() {
        LocalDate date = LocalDate.of(2021, 5, 31);
        LocalTime time = LocalTime.of(23, 55);

        guild = new Guild("1234567890");
        task = new Task("Adpro", date, time);

        LocalDate dateReminder = LocalDate.of(2021, 5, 29);
        LocalTime timeReminder = LocalTime.of(20, 15);
        String randomIDChannel = "1234567890";
        reminder = new Reminder(dateReminder, timeReminder, randomIDChannel);
    }

    @Test
    void testServiceCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        String idGuild = guild.getIdGuild();
        when(guildRepository.findByIdGuild(idGuild)).thenReturn(guild);

        Task createdTask = taskServiceImpl.createTask(task, guild.getIdGuild());
        Assertions.assertEquals(createdTask.getName(), task.getName());
    }

    @Test
    void testServiceDeleteTask() {
        task.setGuild(guild);
        Integer idTask = task.getIdTask();
        when(taskRepository.findByIdTask(idTask)).thenReturn(task);

        taskServiceImpl.deleteTask(idTask);

        List<Task> listTasks = guild.getTaskList();
        Assertions.assertEquals(0, listTasks.size());
    }

    @Test
    void testServiceUpdateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        String idGuild = guild.getIdGuild();
        when(guildRepository.findByIdGuild(idGuild)).thenReturn(guild);

        LocalDate oldDate = task.getDate();
        Task savedTask = taskServiceImpl.createTask(task, idGuild);

        Integer idTask = savedTask.getIdTask();
        LocalDate newDate = LocalDate.of(2021, 6, 5);
        LocalTime oldTime = savedTask.getTime();

        when(taskRepository.findByIdTask(idTask)).thenReturn(savedTask);
        Task updatedTask = taskServiceImpl.updateTask(idTask, newDate, oldTime);

        Assertions.assertNotEquals(oldDate, updatedTask.getDate());
        Assertions.assertEquals(newDate, updatedTask.getDate());
    }

    @Test
    void testServiceShowAllTask() {
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
    void testServiceShowAtDate() {
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
    void testServiceDetailTask() {
        Integer idTask = task.getIdTask();
        when(taskRepository.findByIdTask(idTask)).thenReturn(task);
        Task returnedTask = taskServiceImpl.detailTask(idTask);

        Assertions.assertEquals(task, returnedTask);
    }

    @Test
    void testServiceFindByIDTask() {
        Integer idTask = task.getIdTask();
        when(taskRepository.findByIdTask(idTask)).thenReturn(task);
        Task returnedTask = taskServiceImpl.findByIDTask(idTask);

        Assertions.assertEquals(task.getName(), returnedTask.getName());
    }

    @Test
    void testServiceSetReminder() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);

        taskServiceImpl.setReminder(reminder, task);
        Assertions.assertEquals(task.getReminders().size(), 1);

    }

    @Test
    void testReminderFindByID() {
        Integer id = reminder.getIdReminder();
        when(reminderRepository.findByIdReminder(id)).thenReturn(reminder);

        Reminder returnedReminder = taskServiceImpl.findByIDReminder(id);
        Assertions.assertEquals(reminder, returnedReminder);
    }

    @Test
    void testReminderFindAllReminder() {
        List<Reminder> listReminder = new ArrayList<>();
        listReminder.add(reminder);
        when(reminderRepository.findAll()).thenReturn(listReminder);

        List<Reminder> returnedList = taskServiceImpl.findAllReminder();
        Assertions.assertEquals(listReminder, returnedList);
    }

    @Test
    void testReminderDeleteByID() {
        reminder.setTask(task);
        Integer idReminder = reminder.getIdReminder();
        when(reminderRepository.findByIdReminder(idReminder)).thenReturn(reminder);

        taskServiceImpl.deleteReminder(idReminder);

        Set<Reminder> listReminders = task.getReminders();
        Assertions.assertEquals(0, listReminders.size());
    }

}
