package com.adpro.remind.controller;

import com.adpro.remind.command.Command;
import com.adpro.remind.repository.CommandRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;


@Component
public class FeatureCommandImpl implements FeatureCommand {

    private CommandRepository commandRepository;

    @Autowired
    public FeatureCommandImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public String formatCommand(String[] inputContent) {
        String commandName;
        if (inputContent.length == 1) {
            commandName = inputContent[0].substring(1);
        } else {
            commandName = inputContent[0].substring(1) + " " + inputContent[1];
        }
        return commandName;
    }

    @Override
    public void outputMessage(Message message, String[] inputContent) {

        try {
            Command command = commandRepository.getCommand(formatCommand(inputContent));
            MessageEmbed content = command.getOutputMessage(message, inputContent);
            RestAction<Message> action = message.getChannel().sendMessage(content);
            action.queue();

        } catch (Exception e) {
            e.printStackTrace();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.addField("Perintah yang Anda masukan salah, coba lagi.","", false);
            RestAction<Message> action = message.getChannel().sendMessage(eb.build());
            action.queue();
        }
    }

    @Override
    public void outputPrivateMessage(Message message, String[] inputContent) {
        RestAction<PrivateChannel> action = message.getAuthor().openPrivateChannel();
        try {
            Command command = commandRepository.getCommand(formatCommand(inputContent));
            MessageEmbed content = command.getOutputMessage(message, inputContent);
            action.queue((channel) -> channel.sendMessage(content).queue());

        } catch (Exception e) {
            e.printStackTrace();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.addField("Perintah yang Anda masukan salah, coba lagi.","", false);
            action.queue((channel) -> channel.sendMessage(eb.build()).queue());
        }
    }

}