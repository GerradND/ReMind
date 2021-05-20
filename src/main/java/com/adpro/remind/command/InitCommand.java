package com.adpro.remind.command;

import com.adpro.remind.command.reminder.ReminderAddCommand;
import com.adpro.remind.command.schedule.ScheduleAddCommand;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.service.ScheduleService;
import com.adpro.remind.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitCommand {

    private CommandRepository commandRepository;
    private ScheduleService scheduleService;
    private TaskService taskService;

    @Autowired
    public InitCommand(CommandRepository commandRepository, ScheduleService scheduleService, TaskService taskService) {
        this.commandRepository = commandRepository;
        this.scheduleService = scheduleService;
        this.taskService = taskService;
    }

    @PostConstruct
    public void init() {
        commandRepository.addCommand("schedule add", new ScheduleAddCommand(scheduleService));
        commandRepository.addCommand("reminder add", new ReminderAddCommand(taskService));

    }

}
