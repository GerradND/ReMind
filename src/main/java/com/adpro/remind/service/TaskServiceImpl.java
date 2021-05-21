package com.adpro.remind.service;

import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.ReminderRepository;
import com.adpro.remind.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TaskServiceImpl implements TaskService{

    private TaskRepository taskRepository;
    private ReminderRepository reminderRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ReminderRepository reminderRepository){
        this.taskRepository = taskRepository;
        this.reminderRepository = reminderRepository;
    }

    @Override
    public Task createTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteTask(Integer idTask) {
        Task task = taskRepository.findByIdTask(idTask);
        taskRepository.delete(task);
    }

    @Override
    public Task updateTask(Integer idTask, LocalDate date, LocalTime time) {
        Task task = taskRepository.findByIdTask(idTask);
        task.setDate(date);
        task.setTime(time);
        taskRepository.save(task);

        return task;
    }

    @Override
    public Iterable<Task> showAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Iterable<Task> showTaskAtDate(LocalDate date) {
        return taskRepository.findByDate(date);
    }

    @Override
    public Task detailTask(Integer idTask) {
        return taskRepository.findByIdTask(idTask);
    }

    @Override
    public Reminder setReminder(Reminder reminder, Task task) {
        task.setReminder(reminder);
        reminder.setTask(task);
        reminderRepository.save(reminder);
        taskRepository.save(task);
        return reminder;
    }

    @Override
    public Task findByIDTask(Integer idTask){
        return taskRepository.findByIdTask(idTask);
    }
}
