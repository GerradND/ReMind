package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class HelpScheduleCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Schedule Command List");
        eb.appendDescription("`-schedule ADD [NAMA_SCHEDULE] [HARI] [JAM] [DESC]`\n" +
                "`-schedule DELETE [ID_SCHEDULE]`\n" +
                "`-schedule UPDATE [ID_SCHEDULE] [HARI_BARU] [JAM_BARU]`\n" +
                "`-schedule UPDATEDESC [ID_SCHEDULE] [DESC_BARU]`\n" +
                "`-schedule SHOW [HARI]`\n" +
                "`-schedule SHOW ALL`\n" +
                "\n" +
                "Notes:\n" +
                "1. Format HARI: Senin, Selasa, dsb.\n" +
                "2. Format JAM: HH:MM\n" +
                "4. Untuk melihat ID suatu Schedule, gunakan -schedule SHOW ALL atau -schedule SHOW [HARI]\n");

        message.getChannel().sendMessage(eb.build()).queue();
    }
}
