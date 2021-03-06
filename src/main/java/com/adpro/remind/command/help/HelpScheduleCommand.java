package com.adpro.remind.command.help;

import com.adpro.remind.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class HelpScheduleCommand implements Command {
    private EmbedBuilder embedOutput;

    public EmbedBuilder getEmbedOutput() {
        return embedOutput;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Schedule Command List");
        eb.appendDescription("`-schedule add [NAMA_SCHEDULE] [HARI] [JAM_MULAI] [JAM_SELESAI] [DESC]`\n" +
                "`-schedule delete [ID_SCHEDULE]`\n" +
                "`-schedule update [ID_SCHEDULE] [HARI_BARU] [JAM_MULAI] [JAM_SELESAI]`\n" +
                "`-schedule updatedesc [ID_SCHEDULE] [JUDUL_BARU] [DESC_BARU]`\n" +
                "`-schedule show [ID_SCHEDULE]`\n" +
                "`-schedule show [HARI]`\n" +
                "`-schedule show all`\n" +
                "`-schedule notify`\n" +
                "`-schedule notifyset [JAM]`\n" +
                "\n" +
                "Notes:\n" +
                "1. Format HARI: Monday, Tuesday, .., Sunday\n" +
                "2. Format JAM: HH:MM\n" +
                "3. Format NAMA_SCHEDULE dituliskan tanpa ada spasi\n" +
                "4. Gunakan `-schedule notify` untuk mengaktifkan dan mematikan notifikasi\n" +
                "5. Notifikasi berlangsung pada detik pertama (nol) dari jam yang ditentukan\n" +
                "6. Disclaimer: Waktu notifikasi dalam WIB :flag_id:\n" +
                "7. Untuk melihat ID suatu Schedule, gunakan `-schedule show all` atau `-schedule show [HARI]`\n");
        embedOutput = eb;

        message.getChannel().sendMessage(eb.build()).queue();
    }
}
