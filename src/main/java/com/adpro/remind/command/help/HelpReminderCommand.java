package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class HelpReminderCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Reminder Command List");
        eb.appendDescription("`-reminder ADD [NAMA_REMINDER] [TANGGAL] [JAM]`\n" +
                "`-reminder DELETE [ID_REMINDER]`\n" +
                "`-reminder UPDATE [ID_REMINDER] [TANGGAL_BARU] [JAM_BARU]`\n" +
                "`-reminder SHOW ALL`\n" +
                "`-reminder SHOW [TANGGAL]`\n" +
                "`-reminder DETAIL [ID_REMINDER]`\n" +
                "`-reminder SET [ID_REMINDER] [WAKTU_REMINDER]`\n" +
                "\n" +
                "Notes:\n" +
                "1. Format TANGGAL: DD/MM/YYYY\n" +
                "2. Format JAM: HH:MM\n" +
                "3. Format WAKTU: X Hari atau X Jam\n" +
                "4. Untuk melihat ID suatu Reminder, gunakan -reminder SHOW ALL atau -reminder SHOW [TANGGAL]\n");

        message.getChannel().sendMessage(eb.build()).queue();
    }
}
