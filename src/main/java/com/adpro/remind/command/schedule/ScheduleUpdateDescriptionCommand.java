package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ScheduleUpdateDescriptionCommand implements Command {

    private ScheduleService scheduleService;
    private String outputMsg;

    public ScheduleUpdateDescriptionCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public String formDescription(String[] inputContent, int start) {
        StringBuilder desc = new StringBuilder();
        for (int i = start; i < inputContent.length - 1; i++) {
            desc.append(inputContent[i]).append(" ");
        }
        desc.append(inputContent[inputContent.length - 1]);
        return desc.toString();
    }

    public String getOutputMsg() {
        return outputMsg;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        String idGuild = message.getGuild().getId();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            String newTitle = inputContent[3];
            String newDesc = formDescription(inputContent, 4);
            Schedule schedule = scheduleService.getScheduleByID(idSchedule);

            if (schedule == null || !schedule.getGuild().getIdGuild().equals(idGuild)) {
                outputMsg = "Schedule dengan ID: " + idSchedule + " tidak ditemukan!";
                eb.setColor(Color.orange);
                eb.addField(outputMsg, "", false);

            } else {
                String oldTitle = schedule.getTitle();
                schedule.setTitle(newTitle);
                schedule.setDescription(newDesc);
                scheduleService.updateSchedule(idSchedule, schedule);
                outputMsg = ":pencil2: Keterangan schedule \"" + oldTitle + "\" berhasil diubah!";
                eb.setColor(Color.green);
                eb.setTitle(outputMsg);
                eb.addField("✍ Judul baru: ", newTitle, false);
                eb.addField(":memo: Deskripsi baru: ", newDesc, false);
            }

            message.getChannel().sendMessage(eb.build()).queue();


        } catch (NumberFormatException e) {
            outputMsg = "Tolong masukan ID yang valid.";
            eb.setColor(Color.red);
            eb.addField(outputMsg, "", false);
            message.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
