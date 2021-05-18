package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.entities.Message;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class ScheduleAddCommand implements Command {

    private ScheduleService scheduleService;

    public ScheduleAddCommand(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    public String formDescription(String[] inputContent) {
        String description = "";
        for(int i = 6; i < inputContent.length; i++) {
            description += inputContent[i] + " ";
        }
        return description;
    }

    public static DayOfWeek getDayOfWeek(String day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.US);
        TemporalAccessor accessor = formatter.parse(day);
        return DayOfWeek.from(accessor);
    }

    public static LocalTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dateTime = LocalTime.parse(time, formatter);

        return dateTime;
    }

    public void getOutputMessage(Message message, String[] inputContent) {
        String title = inputContent[2];
        DayOfWeek day = getDayOfWeek(inputContent[3]);
        LocalTime startTime = getTime(inputContent[4]);
        LocalTime endTime = getTime(inputContent[5]);
        String desc = formDescription(inputContent);

        scheduleService.createSchedule(new Schedule(title, day, startTime, endTime, desc));

        message.getChannel().sendMessage("Schedule " + title + " berhasil ditambahkan!").queue();
    }

}
