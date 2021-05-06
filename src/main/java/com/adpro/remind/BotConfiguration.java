package com.adpro.remind;

import com.adpro.remind.controller.schedule.ScheduleCommand;
import com.adpro.remind.events.EventListener;
import com.adpro.remind.repository.ScheduleRepository;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);
    private final char prefix = '-';
    private ScheduleCommand scheduleCommand;

    @Autowired
    private ScheduleRepository scheduleRepository;

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
            // imperative way, might change later
            client.getEventDispatcher().on(MessageCreateEvent.class)
                    .subscribe(event -> {
                        final String content = event.getMessage().getContent();
                        String[] components = content.split("\\s");

                        if (content.startsWith(prefix + "schedule")) {
                            scheduleCommand = new ScheduleCommand(components, components[1]);
                            event.getMessage()
                                    .getChannel().block()
                                    .createMessage(scheduleCommand.getOutputMessage()).block();

                        } else if (content.startsWith(prefix + "reminder")) {

                        } else if (content.startsWith(prefix + "todolist")) {

                        } else if (content.startsWith(prefix + "ping")) {
                            event.getMessage()
                                    .getChannel().block()
                                    .createMessage("Pong!").block();
                        } else if (content.startsWith(String.valueOf(prefix))) {
                            event.getMessage()
                                    .getChannel().block()
                                    .createMessage("Command yang anda masukan salah, silahkan coba lagi!").block();
                        }
                    });

            client.onDisconnect().block();
        }
        catch ( Exception exception ) {
            log.error( "Be sure to use a valid bot token!", exception );
        }

        return client;
    }
}