package com.adpro.remind.event;

import com.adpro.remind.controller.FeatureCommand;
import com.adpro.remind.service.GuildService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InputEventListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(InputEventListener.class);

    @Value("${prefix}")
    private String prefix;
    private String[] content;

    @Autowired
    private GuildService guildService;

    @Autowired
    private FeatureCommand featureCommand;

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)  {
        Message message = event.getMessage();
        String idGuild = message.getGuild().getId();
        guildService.createGuild(idGuild);
        content = message.getContentRaw().split(" ");

        if (message.getAuthor().isBot()) {
            return;
        }

        if (message.getContentRaw().startsWith(prefix)) {
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
