package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class HelpToDoListCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("ToDoList Command List");
        eb.appendDescription("`-list ADD [NAMA_LIST]`\n" +
                "`-list ADDITEM [ID_LIST] [NAMA_ITEM]`\n" +
                "`-list DELETE [ID_LIST] [NOMOR_ITEM]`\n" +
                "`-list DELETE [ID_LIST]`\n" +
                "`-list SHOW [ID_LIST]`\n" +
                "`-list SHOW ALL`\n" +
                "\n" +
                "Notes:\n" +
                "1. Untuk melihat ID suatu List, gunakan -list SHOW ALL\n"+
                "2. Untuk melihat nomor suatu item pada list, gunakan -list SHOW [ID_LIST]\n");

        message.getChannel().sendMessage(eb.build()).queue();
    }
}
