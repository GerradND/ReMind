package com.adpro.remind.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SendReminderCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb1 = new EmbedBuilder();
        eb1.setTitle("Pertama");
        message.getChannel().sendMessage(eb1.build()).queue();

        String textChannelId = message.getChannel().getId();

        System.out.println("id text channel = " + textChannelId);
        TextChannel textChannel = message.getJDA().getTextChannelById(textChannelId);

        EmbedBuilder eb2 = new EmbedBuilder();
        eb2.setTitle("Kedua");
        textChannel.sendMessage(eb2.build()).queue();
    }
}
