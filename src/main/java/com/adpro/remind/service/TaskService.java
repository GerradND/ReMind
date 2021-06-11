package com.adpro.remind.service;

import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TaskService {
    Task createTask(Task task, String idGuild);
    void deleteTask(Integer idTask);
    Task updateTask(Integer idTask, LocalDate date, LocalTime time);
    Iterable<Task> showAllTask(String idGuild);
    Iterable<Task> showTaskAtDate(LocalDate date, String idGuild);
    Task detailTask(Integer idTask);
    Task findByIDTask(Integer idTask);

    Reminder setReminder(Reminder reminder, Task task);
    Reminder findByIDReminder(Integer idReminder);
    List<Reminder> findAllReminder();
    void deleteReminder(Integer id);
}
