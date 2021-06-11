package com.adpro.remind.command;

import net.dv8tion.jda.api.entities.Message;

public interface Command {
    void getOutputMessage(Message message, String[] inputContent);
}
