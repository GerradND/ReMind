package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class HelpCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("ReMind Bot Command List");
        eb.appendDescription("`-help` untuk menampilkan list ini\n" +
                "`-help reminder` untuk menampilkan list command untuk fitur reminder\n" +
                "`-help schedule` untuk menampilkan list command untuk fitur schedule\n" +
                "`-help list` untuk menampilkan list command untuk fitur todolist\n");

        message.getChannel().sendMessage(eb.build()).queue();
    }
}