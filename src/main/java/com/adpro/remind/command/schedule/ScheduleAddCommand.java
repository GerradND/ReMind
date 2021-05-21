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
import java.time.temporal.TemporalAccessor;
import java.util.Date;
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

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        String title = inputContent[2];
        String day = inputContent[3];
        String startTime = inputContent[4];
        String endTime = inputContent[5];
        String desc = formDescription(inputContent);

        try {
            Schedule schedule = scheduleService.createSchedule(new Schedule(title, getDayOfWeek(day),
                    getTime(startTime), getTime(endTime), desc));

            eb.setTitle(":white_check_mark: Schedule \"" + title + "\" berhasil ditambahkan!");
            eb.addField(":id: Id:", schedule.getIdSchedule().toString(), true);
            eb.addField(":writing_hand: Judul:", title, true);
            eb.addBlankField(true);
            eb.addField(":date: Hari:", day, true);
            eb.addField(":alarm_clock: Jam:", startTime + "-" + endTime, true);
            eb.addBlankField(true);
            eb.addField(":memo: Deskripsi:", desc, true);
            eb.setColor(Color.green);

            message.getChannel().sendMessage(eb.build()).queue();

        } catch (Exception e) {
            eb.addField("Penambahan schedule gagal/terdapat kesalahan parameter. Silahkan coba lagi.","", false);
            eb.setColor(Color.red);
            message.getChannel().sendMessage(eb.build()).queue();
        }

    }

}
