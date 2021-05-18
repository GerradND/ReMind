package com.adpro.remind.command;

import com.adpro.remind.command.schedule.ScheduleAddCommand;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitCommand {

    private CommandRepository commandRepository;
    private ScheduleService scheduleService;

    @Autowired
    public InitCommand(CommandRepository commandRepository, ScheduleService scheduleService) {
        this.commandRepository = commandRepository;
        this.scheduleService = scheduleService;
    }

    @PostConstruct
    public void init() {
        commandRepository.addCommand("schedule add", new ScheduleAddCommand(scheduleService));
    }

}
