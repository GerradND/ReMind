package com.adpro.remind.command;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

@NoArgsConstructor
public class PingCommand implements Command {
    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        long time = System.currentTimeMillis();
        eb.setFooter(String.format("Ping: %d ms", System.currentTimeMillis() - time));
        return eb.build();
    }
}
