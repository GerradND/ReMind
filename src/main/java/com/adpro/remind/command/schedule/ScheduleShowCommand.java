package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class ScheduleShowCommand implements Command {

    public final String[] arrHari = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private ScheduleService scheduleService;

    public ScheduleShowCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }

    private boolean IsInt_ByException(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String idGuild = message.getGuild().getId();
        String outputMsg = "";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.YELLOW);

        if (inputContent[2].equals("all")) {    // semua schedule
            eb.setTitle(":calendar_spiral: List semua schedule");
            Iterable<Schedule> list = scheduleService.getListSchedule(idGuild);

            if(list == null) {
                eb.addField("Schedule Anda masih kosong!", "", false);
            } else {
                for (Schedule schedule : scheduleService.getListSchedule(idGuild)) {
                    eb.addField(":bulb: \"" + schedule.getTitle() + "\" - <:id:: " + schedule.getIdSchedule() + ">",
                            String.format("(%s, %s-%s)",
                                    schedule.getDay().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                                    schedule.getStartTime().toString(),
                                    schedule.getEndTime().toString()), false);
                }
            }

        } else if (IsInt_ByException(inputContent[2])) {    // schedule spesifik
            Schedule schedule = scheduleService.getScheduleByID(Integer.parseInt(inputContent[2]));

            if(schedule == null) {
                eb.addField("Schedule dengan ID: " + inputContent[2] + " tidak dapat ditemukan!", "", false);
            } else {
                eb.setTitle(":yellow_circle: \"" + schedule.getTitle() + "\"");
                eb.addField(":date: " + schedule.getDay().getDisplayName(TextStyle.FULL, Locale.getDefault()), "", false);
                eb.addField(":alarm_clock: " + schedule.getStartTime() + "-" + schedule.getEndTime(), "", false);
                eb.addField(":memo: Deskripsi:", schedule.getDescription(), false);
            }


        } else if (stringContainsItemFromList(inputContent[2], arrHari)) {  // schedule per hari
            Iterable<Schedule> listScheduleDay = scheduleService.getScheduleByDay(inputContent[2].toUpperCase());
            eb.setTitle(":yellow_square: " + inputContent[2]);

            if(listScheduleDay.spliterator().getExactSizeIfKnown() == 0) {
                eb.addField("Schedule Anda kosong untuk hari tersebut :smile:", "", false);
            } else {
                for (Schedule schedule : listScheduleDay) {
                    eb.addField(":bulb: \"" + schedule.getTitle() + "\" - <:id:: " + schedule.getIdSchedule() + ">",
                            String.format("(%s-%s)",
                                    schedule.getStartTime().toString(),
                                    schedule.getEndTime().toString()), false);
                }
            }
        }
        message.getChannel().sendMessage(eb.build()).queue();
    }
}
