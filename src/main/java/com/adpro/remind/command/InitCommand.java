package com.adpro.remind.command;

import com.adpro.remind.command.help.HelpCommand;
import com.adpro.remind.command.help.HelpReminderCommand;
import com.adpro.remind.command.help.HelpScheduleCommand;
import com.adpro.remind.command.help.HelpToDoListCommand;
import com.adpro.remind.command.schedule.ScheduleAddCommand;
import com.adpro.remind.command.reminder.*;
import com.adpro.remind.command.schedule.*;
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
        commandRepository.addCommand("help", new HelpCommand());
        commandRepository.addCommand("help reminder", new HelpReminderCommand());
        commandRepository.addCommand("help schedule", new HelpScheduleCommand());
        commandRepository.addCommand("help list", new HelpToDoListCommand());
        commandRepository.addCommand("ping", new PingCommand());
        commandRepository.addCommand("schedule add", new ScheduleAddCommand(scheduleService));
        commandRepository.addCommand("reminder add", new ReminderAddCommand(taskService));
        commandRepository.addCommand("reminder delete", new ReminderDeleteCommand(taskService));
        commandRepository.addCommand("reminder update", new ReminderUpdateCommand(taskService));
        commandRepository.addCommand("reminder show", new ReminderShowCommand(taskService));
        commandRepository.addCommand("reminder detail", new ReminderDetailCommand(taskService));
        commandRepository.addCommand("reminder set", new ReminderSetCommand(taskService));
        commandRepository.addCommand("schedule update", new ScheduleTimeUpdateCommand(scheduleService));
        commandRepository.addCommand("schedule updatedesc", new ScheduleDescriptionUpdateCommand(scheduleService));
        commandRepository.addCommand("schedule delete", new ScheduleDeleteCommand(scheduleService));
        commandRepository.addCommand("schedule show", new ScheduleShowCommand(scheduleService));
    }

}
