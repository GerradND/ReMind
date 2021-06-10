package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleNotifySetTimeCommand implements Command {
    private GuildService guildService;
    private ScheduleService scheduleService;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private HashMap<String, ScheduledFuture<?>> subscriber;
    private List<Schedule> scheduleListDay;
    private ScheduledFuture<?> notifyHandle;
    private String outputMsg;

    public List<Schedule> getScheduleListDay() {
        return scheduleListDay;
    }

    public String getOutputMsg() {
        return outputMsg;
    }

    public ScheduleNotifySetTimeCommand(GuildService guildService, ScheduleService scheduleService) {
        this.guildService = guildService;
        this.scheduleService = scheduleService;
    }

    public void notificationMessage(Message message, String idGuild, String today) {
        EmbedBuilder eb = new EmbedBuilder();
        scheduleListDay = scheduleService.getScheduleByDay(today, idGuild);
        eb.setTitle(":yellow_square: " + today);

        if(scheduleListDay.isEmpty()) {
            outputMsg = "Schedule Anda kosong untuk hari " + today + " :smile:";
            eb.addField(outputMsg, "", false);

        } else {
            outputMsg = "Berikut adalah Schedule Anda untuk hari " + today + ":";
            eb.addField(outputMsg, "", false);
            for (Schedule schedule : scheduleListDay) {
                eb.addField(
                        String.format(":bulb: \"%s\" - <:id::%s>", schedule.getTitle(), schedule.getIdSchedule()),
                        String.format("(%s-%s)", schedule.getStartTime().toString(), schedule.getEndTime().toString()),
                        false);
            }
        }
        message.getChannel().sendMessage(eb.build()).queue();
    }

    public ScheduledFuture<?> notifyOn(Message message, String idGuild) {
        scheduleListDay = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        int hours = guildService.getNotifyTimeSchedule(idGuild).getHour();
        int minute = guildService.getNotifyTimeSchedule(idGuild).getMinute();
        String today = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        LocalDateTime nextRun = now.withHour(hours).withMinute(minute).withSecond(0);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);

        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();

        final Runnable notifier = new Runnable() {
            public void run() {
                notificationMessage(message, idGuild, today);
            }
        };
        this.notifyHandle = scheduler.scheduleAtFixedRate(notifier, initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS); // -> set sesuai timer
        return notifyHandle;

    }

    public void notifyOff(ScheduledFuture<?> notifyHandle) {
        notifyHandle.cancel(true);
    }

    public LocalTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }

    public void changeNotifyTime(Message message, String idGuild) {
        EmbedBuilder eb = new EmbedBuilder();

        if (subscriber.containsKey(idGuild))
        {
            outputMsg = "Notifikasi aktif dan berhasil diubah menjadi jam " +
                    guildService.getNotifyTimeSchedule(idGuild).toString() + " tiap harinya.";
            notifyOff(subscriber.get(idGuild));
            subscriber.replace(idGuild, notifyOn(message, idGuild));
            eb.setColor(Color.ORANGE);

        } else {
            outputMsg = "Notifikasi aktif! Schedule Anda akan dinotifikasikan jam " +
                    guildService.getNotifyTimeSchedule(idGuild).toString() + " tiap harinya.";
            subscriber.put(idGuild, notifyOn(message, idGuild));
            guildService.notifySchedule(idGuild);
            eb.setColor(Color.GREEN);
        }
        eb.setDescription(outputMsg);
        message.getChannel().sendMessage(eb.build()).queue();

    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        this.subscriber = guildService.getScheduleSubscriber();

        String idGuild = message.getGuild().getId();
        String time = inputContent[2];

        guildService.setNotifyTimeSchedule(idGuild, getTime(time));
        changeNotifyTime(message, idGuild);
    }
}
