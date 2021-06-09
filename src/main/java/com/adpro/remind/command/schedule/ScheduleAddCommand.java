package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.event.InputEventListener;
import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.hibernate.id.GUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class ScheduleAddCommand implements Command {

    private ScheduleService scheduleService;
    private String outputMsg;

    public ScheduleAddCommand(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    public String formDescription(String[] inputContent) {
        StringBuilder description = new StringBuilder();
        for(int i = 6; i < inputContent.length-1; i++) {
            description.append(inputContent[i]).append(" ");
        }
        description.append((inputContent[inputContent.length-1]));
        return description.toString();
    }

    public DayOfWeek getDayOfWeek(String day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.US);
        TemporalAccessor accessor = formatter.parse(day);
        return DayOfWeek.from(accessor);
    }

    public LocalTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dateTime = LocalTime.parse(time, formatter);

        return dateTime;
    }

    public String getOutputMsg() {
        return outputMsg;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        String title = inputContent[2];
        String day = inputContent[3];
        String startTime = inputContent[4];
        String endTime = inputContent[5];
        String desc = formDescription(inputContent);

        String idGuild = message.getGuild().getId();
        System.out.println(idGuild);

        try {
            Schedule schedule = new Schedule(title, getDayOfWeek(day), getTime(startTime), getTime(endTime), desc);
            schedule = scheduleService.createSchedule(schedule, idGuild);

            outputMsg = ":white_check_mark: Schedule \"" + title + "\" berhasil ditambahkan!";
            eb.setTitle(outputMsg);

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
            e.printStackTrace();
            outputMsg = "Penambahan schedule gagal/terdapat kesalahan parameter. Silahkan coba lagi.";
            eb.addField(outputMsg, "", false);
            eb.setColor(Color.red);
            message.getChannel().sendMessage(eb.build()).queue();
        }

    }

}
