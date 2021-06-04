package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class ScheduleUpdateDescriptionCommand implements Command {

    private ScheduleService scheduleService;
    private EmbedBuilder eb;
    private String outputMsg;

    public ScheduleUpdateDescriptionCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public Schedule updateSchedule(int idSchedule, String title, String desc) {
        Schedule ongoingSchedule = null;
        if(scheduleService.getScheduleByID(idSchedule) != null) {
            ongoingSchedule = scheduleService.getScheduleByID(idSchedule);
            ongoingSchedule.setTitle(title);
            ongoingSchedule.setDescription(desc);
        }
        return ongoingSchedule;
    }

    public String formDescription(String[] inputContent, int start) {
        StringBuilder desc = new StringBuilder();
        for(int i = start; i < inputContent.length-1; i++) {
            desc.append(inputContent[i]).append(" ");
        }
        desc.append(inputContent[inputContent.length-1]);
        return desc.toString();
    }

    public String getOutputMsg() {
        return outputMsg;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        eb = new EmbedBuilder();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            String title = inputContent[3];
            String desc = formDescription(inputContent, 4);

            if(updateSchedule(idSchedule, title, desc) == null) {
                eb.setColor(Color.orange);
                outputMsg = "Schedule dengan ID: " + idSchedule + " tidak ditemukan!";
                eb.addField(outputMsg, "", false);

            } else {
                Schedule updatedSchedule = updateSchedule(idSchedule, title, desc);
                scheduleService.updateSchedule(idSchedule, updatedSchedule);
                eb.setColor(Color.green);
                outputMsg = ":pencil2: Keterangan schedule \"" + updatedSchedule.getTitle() + "\" berhasil diubah!";
                eb.setTitle(outputMsg);
                eb.addField("✍ Judul: ", updatedSchedule.getTitle(), false);
                eb.addField(":memo: Deskripsi: ", updatedSchedule.getDescription(), false);
            }

            message.getChannel().sendMessage(eb.build()).queue();


        } catch (NumberFormatException e) {
            eb.setColor(Color.red);
            outputMsg = "Tolong masukan ID yang valid.";
            eb.addField(outputMsg, "", false);
            message.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
