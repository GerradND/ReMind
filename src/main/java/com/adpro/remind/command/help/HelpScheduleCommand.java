package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class HelpScheduleCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Schedule Command List");
        eb.appendDescription("`-schedule add [NAMA_SCHEDULE] [HARI] [JAM_MULAI] [JAM_SELESAI] [DESC]`\n"
                + "`-schedule delete [ID_SCHEDULE]`\n"
                + "`-schedule update [ID_SCHEDULE] [HARI_BARU] [JAM_MULAI] [JAM_SELESAI]`\n"
                + "`-schedule updatedesc [ID_SCHEDULE] [JUDUL_BARU] [DESC_BARU]`\n"
                + "`-schedule show [ID_SCHEDULE]`\n"
                + "`-schedule show [HARI]`\n"
                + "`-schedule show all`\n"
                + "`-schedule notify`\n"
                + "`-schedule notifyset [JAM]`\n"
                + "\n"
                + "Notes:\n"
                + "1. Format HARI: Monday, Tuesday, .., Sunday\n"
                + "2. Format JAM: HH:MM\n"
                + "3. Disclaimer: Waktu dalam WIB :flag_id:\n"
                + "4. Untuk melihat ID suatu Schedule, gunakan `-schedule show all` atau `-schedule show [HARI]`\n");

        message.getChannel().sendMessage(eb.build()).queue();
    }
}
