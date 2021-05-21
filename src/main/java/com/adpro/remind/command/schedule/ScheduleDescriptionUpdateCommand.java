package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class ScheduleDescriptionUpdateCommand implements Command {

    private ScheduleService scheduleService;

    public ScheduleDescriptionUpdateCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public Schedule updateSchedule(int idSchedule, String title, String desc) {
        Schedule schedule = scheduleService.getScheduleByID(idSchedule);
        schedule.setTitle(title);
        schedule.setDescription(desc);
        return schedule;
    }

    public String formDescription(String[] inputContent, int start) {
        String desc = "";
        for(int i = start; i < inputContent.length; i++) {
            desc += inputContent[i] + " ";
        }
        return desc;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            String title = inputContent[3];
            String desc = formDescription(inputContent, 4);

            Schedule updatedSchedule = this.updateSchedule(idSchedule, title, desc);

            if(updatedSchedule == null) {
                eb.setColor(Color.orange);
                eb.addField("Schedule dengan ID: " + idSchedule + "tidak ditemukan!", "", false);

            } else {
                scheduleService.updateSchedule(idSchedule, updatedSchedule);
                eb.setColor(Color.green);
                eb.setTitle(":pencil2: Keterangan schedule \"" + updatedSchedule.getTitle() + "\" berhasil diubah!");
                eb.addField("✍ Judul: ", updatedSchedule.getTitle(), false);
                eb.addField(":memo: Deskripsi: ", updatedSchedule.getDescription(), false);
            }

            message.getChannel().sendMessage(eb.build()).queue();


        } catch (NumberFormatException e) {
            eb.setColor(Color.red);
            eb.addField("Tolong masukan ID yang valid.", "", false);
            message.getChannel().sendMessage(eb.build()).queue();

        }
    }
}
