package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class HelpReminderCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Reminder Command List");
        eb.appendDescription("`-reminder add [NAMA_REMINDER] [TANGGAL] [JAM]`\n" +
                "`-reminder delete [ID_REMINDER]`\n" +
                "`-reminder update [ID_REMINDER] [TANGGAL_BARU] [JAM_BARU]`\n" +
                "`-reminder show all`\n" +
                "`-reminder show [TANGGAL]`\n" +
                "`-reminder detail [ID_REMINDER]`\n" +
                "`-reminder set [ID_REMINDER] [WAKTU_REMINDER]`\n" +
                "\n" +
                "Notes:\n" +
                "1. Format TANGGAL: DD/MM/YYYY\n" +
                "2. Format JAM: HH:MM\n" +
                "3. Format WAKTU: X hari atau X jam\n" +
                "4. Untuk melihat ID suatu Reminder, gunakan `-reminder show all` atau `-reminder show [TANGGAL]`\n");

        message.getChannel().sendMessage(eb.build()).queue();
    }
}
