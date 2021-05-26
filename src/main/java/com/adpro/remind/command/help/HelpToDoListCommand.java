package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class HelpToDoListCommand implements Command {
    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("ToDoList Command List");
        eb.appendDescription("`-list add [NAMA_LIST]`\n" +
                "`-list additem [ID_LIST] [NAMA_ITEM]`\n" +
                "`-list delete [ID_LIST] [NOMOR_ITEM]`\n" +
                "`-list delete [ID_LIST]`\n" +
                "`-list show [ID_LIST]`\n" +
                "`-list showall`\n" +
                "\n" +
                "Notes:\n" +
                "1. Untuk melihat ID suatu List, gunakan -list SHOW ALL\n"+
                "2. Untuk melihat nomor suatu item pada list, gunakan -list SHOW [ID_LIST]\n");

        return eb.build();
    }
}
