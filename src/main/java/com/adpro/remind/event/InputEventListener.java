package com.adpro.remind.event;

import com.adpro.remind.command.help.HelpCommand;
import com.adpro.remind.command.list.ToDoListCommand;
import com.adpro.remind.command.reminder.ReminderCommand;
import com.adpro.remind.command.schedule.ScheduleAddCommand;
import com.adpro.remind.controller.FeatureCommand;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InputEventListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(InputEventListener.class);

    @Value("${prefix}")
    private String prefix;
    private String[] content;
    private Map<String, String> Command = new HashMap<String, String>();

    @Autowired
    private FeatureCommand featureCommand;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        content = message.getContentRaw().split(" ");

        if (message.getAuthor().isBot()) return;

        if (message.getContentRaw().startsWith(prefix)) {
            if(content.length > 1){
                try {
                    featureCommand.outputMessage(message, content);
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
}
