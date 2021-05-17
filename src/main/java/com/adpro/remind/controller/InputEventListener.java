package com.adpro.remind.controller;
import com.adpro.remind.controller.help.HelpCommand;
import com.adpro.remind.controller.list.ToDoListCommand;
import com.adpro.remind.controller.reminder.ReminderCommand;
import com.adpro.remind.controller.schedule.ScheduleCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InputEventListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(InputEventListener.class);

    @Value("${prefix}")
    private String prefix;
    private String output;
    private String[] content;
    private Map<String, String> Command = new HashMap<String, String>();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        content = message.getContentRaw().split(" ");

        if (message.getAuthor().isBot()) return;

        if (message.getContentRaw().startsWith(prefix)) {
            if(content.length > 1){
                try {
                    init();
                    message.getChannel().sendMessage(this.getOutputMessage()).queue();
                } catch (Exception ex) {
                    message.getChannel().sendMessage(
                            "There was an error in your command..."
                    ).queue();
                    logger.error("Failed to process message: " + message.getContentRaw());
                    ex.printStackTrace();
                }
            }
        }
    }

    private void init(){
        ReminderCommand reminderCommand = new ReminderCommand(content, content[1]);
        ScheduleCommand scheduleCommand = new ScheduleCommand(content, content[1]);
        ToDoListCommand toDoListCommand = new ToDoListCommand(content, content[1]);
        HelpCommand helpCommand = new HelpCommand(content, content[1]);
        Command.put("-reminder", reminderCommand.getOutputMessage());
        Command.put("-schedule", scheduleCommand.getOutputMessage());
        Command.put("-list", toDoListCommand.getOutputMessage());
        Command.put("-help", helpCommand.getOutputMessage());
    }

    public String getOutputMessage() {
        return Command.get(content[0]);
    }
}