package com.adpro.remind.controller;

import net.dv8tion.jda.api.entities.Message;


public interface FeatureCommand {
    void outputMessage(Message message, String[] inputContent);
    //void outputPrivateMessage(Message message, String[] inputContent);
}
