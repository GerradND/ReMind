package com.adpro.remind.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public interface Command{
    MessageEmbed getOutputMessage(Message message, String[] inputContent);

}
