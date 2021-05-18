package com.adpro.remind.service;

import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.ReminderRepository;
import com.adpro.remind.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class TaskServiceImpl implements TaskService{

    private TaskRepository taskRepository;
    private ReminderRepository reminderRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
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
    public Task updateTask(Integer idTask, Task updateTask) {
        Task task = taskRepository.findByIdTask(idTask);
        task = updateTask;
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
    public Reminder setReminder(Reminder reminder) {
        reminderRepository.save(reminder);
        return reminder;
    }
}
