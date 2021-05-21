package com.adpro.remind.controller;

import com.adpro.remind.command.Command;
import com.adpro.remind.repository.CommandRepository;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FeatureCommandImpl implements FeatureCommand {

    private CommandRepository commandRepository;

    @Autowired
    public FeatureCommandImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public String formatCommand(String[] inputContent) {
        String feature = inputContent[0].substring(1);
        String operation = inputContent[1];
        return feature + " " + operation;
    }

    @Override
    public void outputMessage(Message message, String[] inputContent) {
        try {
            Command command = commandRepository.getCommand(formatCommand(inputContent));
            command.getOutputMessage(message, inputContent);

        } catch (Exception e) {
            e.printStackTrace();
            message.getChannel().sendMessage("Perintah yang Anda masukan salah, coba lagi.").queue();
        }
    }

}