package com.adpro.remind;

import com.adpro.remind.controller.TodoController;
import com.adpro.remind.events.EventListener;
import com.sun.tools.javac.comp.Todo;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.regex.Pattern;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger( BotConfiguration.class );

    @Value("${token}")
    private String token;

    @Autowired
    private TodoController todoController;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = null;
        try {
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();
            Pattern pattern = Pattern.compile(REGEX);
            client.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
                final User self = event.getSelf();
                System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
            });
            client.getEventDispatcher().on(MessageCreateEvent.class).subscribe(event -> {
                todoController.mainHandler(event);
            });

            client.onDisconnect().block();
        }
        catch ( Exception exception ) {
            log.error( "Be sure to use a valid bot token!", exception );
        }

        return client;
    }
}