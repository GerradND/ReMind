package com.adpro.remind;

import com.adpro.remind.controller.InputController;
import com.adpro.remind.events.EventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger( BotConfiguration.class );
    private final char prefix = '-';

    @Value("${token}")
    private String token;


    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = null;
        try {
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

            client.getEventDispatcher().on(ReadyEvent.class)
                    .subscribe(event -> {
                        final User self = event.getSelf();
                        System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                    });

            client.getEventDispatcher().on(MessageCreateEvent.class)
                    // subscribe is like block, in that it will *request* for action
                    // to be done, but instead of blocking the thread, waiting for it
                    // to finish, it will just execute the results asynchronously.
                    .subscribe(event -> {
                        // 3.1 Message.getContent() is a String
                        final String[] content = event.getMessage().getContent().split(" ");
                        // imperative way, might change later
                        InputController input = new InputController(event, content);
                        event.getMessage()
                                .getChannel().block()
                                .createMessage(input.getOutputMessage()).block();
                    });

            client.onDisconnect().block();
        }
        catch ( Exception exception ) {
            log.error( "Be sure to use a valid bot token!", exception );
        }

        return client;
    }
}
