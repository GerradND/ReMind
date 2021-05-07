package com.adpro.remind;

import com.adpro.remind.core.help.Help;
import com.adpro.remind.core.help.HelpMessage;
import com.adpro.remind.events.EventListener;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger( BotConfiguration.class );

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
            client.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
                final User self = event.getSelf();
                System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));

            });

            final String channelId = "840079805693034496";
            GatewayDiscordClient finalClient = client;
            client.getEventDispatcher().on(MessageCreateEvent.class)
                    .subscribe(event -> {
                        final String content = event.getMessage().getContent();
                        String[] components = content.split("\\s");

                        if (content.startsWith("sendReminder")) {
                            finalClient.getChannelById(Snowflake.of(channelId)).flatMap(channel -> channel.getRestChannel().createMessage("test Reminder"));
                        } else if (content.startsWith("-" + "help")) {
//                            channelId = event.getMessage().getChannelId().asString();
//                            System.out.println(event.getMessage().getChannel().block());
                            String helpMessage;
                            if (content.equals("-help")) {
                                helpMessage = Help.getInstance().help();
                            } else {
                                helpMessage = Help.getInstance().help(components[1]);
                            }
                            event.getMessage()
                                    .getChannel().block()
                                    .createMessage(helpMessage).block();

                        } else if (content.startsWith("ping")) {
                            event.getMessage()
                                    .getChannel().block()
                                    .createMessage("Pong!").block();
                        } else if (content.startsWith("-")) {
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