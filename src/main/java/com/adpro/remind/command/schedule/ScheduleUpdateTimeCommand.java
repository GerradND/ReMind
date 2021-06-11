package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ScheduleUpdateTimeCommand implements Command {

    private ScheduleService scheduleService;
    private String outputMsg;

    public ScheduleUpdateTimeCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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

    public String getOutputMsg() {
        return outputMsg;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        String idGuild = message.getGuild().getId();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            String newDay = inputContent[3];
            String newStartTime = inputContent[4];
            String newEndTime = inputContent[5];
            Schedule schedule = scheduleService.getScheduleByID(idSchedule);

            if (schedule == null || !schedule.getGuild().getIdGuild().equals(idGuild)) {
                outputMsg = "Schedule dengan ID: " + idSchedule + " tidak ditemukan!";
                eb.setColor(Color.orange);
                eb.addField(outputMsg, "", false);

            } else {
                schedule.setDay(getDayOfWeek(newDay));
                schedule.setStartTime(getTime(newStartTime));
                schedule.setEndTime(getTime(newEndTime));
                scheduleService.updateSchedule(idSchedule, schedule);
                outputMsg = ":clock2: Waktu schedule \"" + schedule.getTitle() + "\" berhasil diubah!";
                eb.setColor(Color.green);
                eb.setTitle(outputMsg);
                eb.addField("Hari, Jam",  newDay + ", " + newStartTime + "-" + newEndTime, true);

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
