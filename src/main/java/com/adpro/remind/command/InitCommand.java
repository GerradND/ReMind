package com.adpro.remind.command;

import com.adpro.remind.command.list.*;
import com.adpro.remind.command.schedule.*;
import com.adpro.remind.command.schedule.ListAddTodoItemCommand;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.service.ScheduleService;
import com.adpro.remind.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitCommand {

    private CommandRepository commandRepository;
    private ScheduleService scheduleService;
    @Autowired
    private TodoListService todoListService;

    @Autowired
    public InitCommand(CommandRepository commandRepository, ScheduleService scheduleService) {
        this.commandRepository = commandRepository;
        this.scheduleService = scheduleService;
    }

    @PostConstruct
    public void init() {
        commandRepository.addCommand("ping", new PingCommand());
        commandRepository.addCommand("schedule add", new ScheduleAddCommand(scheduleService));
        commandRepository.addCommand("schedule update", new ScheduleTimeUpdateCommand(scheduleService));
        commandRepository.addCommand("schedule updatedesc", new ScheduleDescriptionUpdateCommand(scheduleService));
        commandRepository.addCommand("schedule delete", new ScheduleDeleteCommand(scheduleService));
        commandRepository.addCommand("schedule show", new ScheduleShowCommand(scheduleService));
        commandRepository.addCommand("list ADD", new ListAddTodoListCommand(todoListService));
        commandRepository.addCommand("list ADDITEM", new ListAddTodoItemCommand(todoListService));
        commandRepository.addCommand("list DELETE", new ListDeleteTodoListCommand(todoListService));
        commandRepository.addCommand("list DELETEITEM", new ListDeleteTodoItemCommand(todoListService));
        commandRepository.addCommand("list SHOWALL", new ListShowAllTodoListCommand(todoListService));
        commandRepository.addCommand("list SHOW", new ListShowTodoListCommand(todoListService));
    }

}
