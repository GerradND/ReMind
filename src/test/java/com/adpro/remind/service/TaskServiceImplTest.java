package com.adpro.remind.service;

import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
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
    ReminderRepository reminderRepository;

    @InjectMocks
    TaskServiceImpl taskServiceImpl;

    private Task task;

    /*
    @BeforeEach
    public void setUp(){
        LocalDate date = LocalDate.of(2021, 05, 31);
        LocalTime time = LocalTime.of(23, 55);
        task = new Task("Adpro", date, time);
    }

    @Test
    void testServiceCreateTask(){
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskServiceImpl.createTask(task);
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
        LocalDate oldDate = task.getDate();
        Task savedTask = taskServiceImpl.createTask(task);

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
        List<Task> listTasks = new ArrayList<Task>();
        listTasks.add(task);

        when(taskRepository.findAll()).thenReturn(listTasks);
        Iterable<Task> tasks = taskServiceImpl.showAllTask();

        List<Task> returnedTasks = (List<Task>) tasks;
        Assertions.assertEquals(listTasks, returnedTasks);
    }

    @Test
    void testServiceShowAtDate(){
        List<Task> listTasks = new ArrayList<Task>();
        listTasks.add(task);

        LocalDate date = task.getDate();
        when(taskRepository.findByDate(date)).thenReturn(listTasks);
        Iterable<Task> tasks = taskServiceImpl.showTaskAtDate(date);

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
        Reminder reminder = new Reminder(dateReminder, timeReminder);

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(reminderRepository.save(any(Reminder.class))).thenReturn(reminder);

        taskServiceImpl.setReminder(reminder, task);
        Assertions.assertEquals(task.getReminders().size(), 1);

    }
     */
}
