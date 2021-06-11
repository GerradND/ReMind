package com.adpro.remind.controller;

import com.adpro.remind.command.Command;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.service.GuildService;
import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeatureCommandImpl implements FeatureCommand {

    private CommandRepository commandRepository;

    @Autowired
    private GuildService guildService;

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
            command.getOutputMessage(message, inputContent);

        } catch (Exception e) {
            e.printStackTrace();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.addField("Perintah yang Anda masukan salah, coba lagi.","", false);
            message.getChannel().sendMessage(eb.build()).queue();
        }
    }
}