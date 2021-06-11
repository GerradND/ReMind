package com.adpro.remind.command;

import com.adpro.remind.command.help.*;
import com.adpro.remind.command.list.*;
import com.adpro.remind.command.reminder.*;
import com.adpro.remind.command.schedule.*;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.ScheduleService;
import com.adpro.remind.service.TaskService;
import com.adpro.remind.service.TodoListService;
import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitCommand {

    private CommandRepository commandRepository;
    private GuildService guildService;
    private ScheduleService scheduleService;
    private TaskService taskService;
    private TodoListService todoListService;

    @Autowired
    public InitCommand(CommandRepository commandRepository, GuildService guildService, ScheduleService scheduleService,
                       TaskService taskService, TodoListService todoListService) {
        this.commandRepository = commandRepository;
        this.guildService = guildService;
        this.scheduleService = scheduleService;
        this.taskService = taskService;
        this.todoListService = todoListService;
    }

    @PostConstruct
    public void init() throws LoginException {
        commandRepository.addCommand("help", new HelpCommand());
        commandRepository.addCommand("help reminder", new HelpReminderCommand());
        commandRepository.addCommand("help schedule", new HelpScheduleCommand());
        commandRepository.addCommand("help list", new HelpToDoListCommand());
        commandRepository.addCommand("ping", new PingCommand());
        commandRepository.addCommand("reminder add", new ReminderAddCommand(taskService));
        commandRepository.addCommand("reminder delete", new ReminderDeleteCommand(taskService));
        commandRepository.addCommand("reminder update", new ReminderUpdateCommand(taskService));
        commandRepository.addCommand("reminder show", new ReminderShowCommand(taskService));
        commandRepository.addCommand("reminder detail", new ReminderDetailCommand(taskService));
        commandRepository.addCommand("reminder set", new ReminderSetCommand(taskService));
        commandRepository.addCommand("schedule add", new ScheduleAddCommand(scheduleService));
        commandRepository.addCommand("schedule update", new ScheduleUpdateTimeCommand(scheduleService));
        commandRepository.addCommand("schedule updatedesc", new ScheduleUpdateDescriptionCommand(scheduleService));
        commandRepository.addCommand("schedule delete", new ScheduleDeleteCommand(scheduleService));
        commandRepository.addCommand("schedule show", new ScheduleShowCommand(scheduleService));
        commandRepository.addCommand("schedule notify", new ScheduleNotifyCommand(guildService, scheduleService));
        commandRepository.addCommand("schedule notifyset", new ScheduleNotifySetTimeCommand(guildService, scheduleService));
        commandRepository.addCommand("list add", new ListAddTodoListCommand(todoListService, guildService));
        commandRepository.addCommand("list additem", new ListAddTodoItemCommand(todoListService));
        commandRepository.addCommand("list delete", new ListDeleteTodoListCommand(todoListService));
        commandRepository.addCommand("list deleteitem", new ListDeleteTodoItemCommand(todoListService));
        commandRepository.addCommand("list show", new ListShowTodoListCommand(todoListService));
        commandRepository.addCommand("list showall", new ListShowAllTodoListCommand(todoListService));
    }

}
