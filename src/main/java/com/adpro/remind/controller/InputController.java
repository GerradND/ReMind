package com.adpro.remind.controller;
import com.adpro.remind.controller.help.HelpCommand;
import com.adpro.remind.controller.list.ToDoListCommand;
import com.adpro.remind.controller.reminder.ReminderCommand;
import com.adpro.remind.controller.schedule.ScheduleCommand;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class InputController {
    private final String PREFIX = "-";
    private String output;
    private String[] content;
    private MessageCreateEvent event;

    public InputController(MessageCreateEvent event, String[] content) {
        this.event = event;
        this.content = content;
    }

    public String getOutputMessage() {
        if (content[0].startsWith(PREFIX + "reminder")) {
            ReminderCommand reminderCommand = new ReminderCommand(content, content[1]);
            output = reminderCommand.getOutputMessage();
        } else if (content[0].startsWith(PREFIX + "schedule")) {
            ScheduleCommand scheduleCommand = new ScheduleCommand(content, content[1]);
            output = scheduleCommand.getOutputMessage();
        } else if (content[0].startsWith(PREFIX + "list")) {
            ToDoListCommand toDoListCommand = new ToDoListCommand(content, content[1]);
            output = toDoListCommand.getOutputMessage();
        } else if (content[0].startsWith(PREFIX + "help")) {
            HelpCommand helpCommand = new HelpCommand(content, content[1]);
            output = helpCommand.getOutputMessage();
        }
        return output;
    }
}
