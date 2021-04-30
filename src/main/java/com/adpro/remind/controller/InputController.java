package com.adpro.remind.controller;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class InputController {
    public static void main(String[] args){
        GatewayDiscordClient client = DiscordClientBuilder.create("ODM3Mjk3MTYxMzQ3MDA2NDc1.YIqfvw.79D0XnAJgGS-dEfaN-KduhnXsog")
                .build()
                .login()
                .block();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().split(" ")[0].equalsIgnoreCase("!Reminder"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(" berhasil dibuat!"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().split(" ")[0].equalsIgnoreCase("!Todo"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("ToDo berhasil dibuat"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().split(" ")[0].equalsIgnoreCase("!Schedule"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Schedule berhasil dibuat!"))
                .subscribe();

        client.onDisconnect().block();
    }
}
