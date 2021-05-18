package com.adpro.remind.service;

import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;

import java.time.LocalDate;

public interface TaskService {
    Task createTask(Task task);
    void deleteTask(Integer idTask);
    Task updateTask(Integer idTask, Task updateTask);
    Iterable<Task> showAllTask();
    Iterable<Task> showTaskAtDate(LocalDate date);
    Task detailTask(Integer idTask);
    Reminder setReminder(Reminder reminder);
}
