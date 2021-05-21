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

public class ScheduleTimeUpdateCommand implements Command {

    private ScheduleService scheduleService;

    public ScheduleTimeUpdateCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public Schedule updateSchedule(int idSchedule, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        Schedule schedule = scheduleService.getScheduleByID(idSchedule);
        schedule.setDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        return schedule;
    }

    public LocalTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }

    public DayOfWeek getDayOfWeek(String day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.US);
        TemporalAccessor accessor = formatter.parse(day);
        return DayOfWeek.from(accessor);
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            DayOfWeek newDay = getDayOfWeek(inputContent[3]);
            LocalTime newStartTime = getTime(inputContent[4]);
            LocalTime newEndTime = getTime(inputContent[5]);


            Schedule updatedSchedule = this.updateSchedule(idSchedule, newDay, newStartTime, newEndTime);
            if(updatedSchedule == null) {
                eb.setColor(Color.orange);
                eb.addField("Schedule dengan ID: " + idSchedule + "tidak ditemukan!", "", false);
                message.getChannel().sendMessage(eb.build()).queue();
            }
            scheduleService.updateSchedule(idSchedule, updatedSchedule);

            eb.setColor(Color.green);
            eb.setTitle(":clock2: Waktu schedule \"" + updatedSchedule.getTitle() + "\" berhasil diubah!");
            eb.addField("Hari, Jam",updatedSchedule.getDay().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ", " +
                            updatedSchedule.getStartTime().toString() + "-" + updatedSchedule.getEndTime().toString(), true);

            message.getChannel().sendMessage(eb.build()).queue();

        } catch (NumberFormatException e) {
            eb.setColor(Color.red);
            eb.addField("Tolong masukan ID yang valid.", "", false);
            message.getChannel().sendMessage(eb.build()).queue();
        }

    }
}
