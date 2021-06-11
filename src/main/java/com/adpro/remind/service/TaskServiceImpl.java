package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ReminderRepository;
import com.adpro.remind.repository.TaskRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ReminderRepository reminderRepository;
    private final GuildRepository guildRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ReminderRepository reminderRepository, GuildRepository guildRepository) {
        this.taskRepository = taskRepository;
        this.reminderRepository = reminderRepository;
        this.guildRepository = guildRepository;
    }

    @Override
    public Task createTask(Task task, String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        task.setGuild(guild);
        guild.getTaskList().add(task);

        taskRepository.save(task);
        guildRepository.save(guild);
        return task;
    }

    @Override
    public void deleteTask(Integer idTask) {
        Task task = taskRepository.findByIdTask(idTask);
        Guild guild = task.getGuild();
        guild.getTaskList().remove(task);
        task.setGuild(null);

        taskRepository.delete(task);
        guildRepository.save(guild);
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
    public Iterable<Task> showAllTask(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        if (guild == null) {
            return null;
        }
        return taskRepository.findByGuild(guild);
    }

    @Override
    public Iterable<Task> showTaskAtDate(LocalDate date, String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        if (guild == null) {
            return null;
        }
        return taskRepository.findByDateAndGuild(date, guild);
    }

    @Override
    public Task detailTask(Integer idTask) {
        return taskRepository.findByIdTask(idTask);
    }

    @Override
    public Task findByIDTask(Integer idTask) {
        return taskRepository.findByIdTask(idTask);
    }

    @Override
    public Reminder setReminder(Reminder reminder, Task task){
        task.setReminder(reminder);
        reminder.setTask(task);
        reminderRepository.save(reminder);
        taskRepository.save(task);
        return reminder;
    }

    @Override
    public Reminder findByIDReminder(Integer idReminder){
        return reminderRepository.findByIdReminder(idReminder);
    }

    @Override
    public List<Reminder> findAllReminder(){
        return reminderRepository.findAll();
    }

    @Override
    public void deleteReminder(Integer id){
        Reminder reminder = reminderRepository.findByIdReminder(id);
        Task task = reminder.getTask();
        task.getReminders().remove(reminder);
        reminder.setTask(null);

        reminderRepository.delete(reminder);
        taskRepository.save(task);
    }

}
