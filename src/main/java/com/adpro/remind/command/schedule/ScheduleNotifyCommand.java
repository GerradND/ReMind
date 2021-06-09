package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ScheduleNotifyCommand implements Command {
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

    public ScheduleNotifyCommand(GuildService guildService, ScheduleService scheduleService){
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
        LocalTime notifyTime = guildService.getNotifyTimeSchedule(idGuild);
        String today = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

        LocalDateTime nextRun = now.withHour(notifyTime.getHour()).withMinute(notifyTime.getHour()).withSecond(0);
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

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        this.subscriber = guildService.getScheduleSubscriber();
        String idGuild = message.getGuild().getId();

        if (!subscriber.containsKey(idGuild)) {
            subscriber.put(idGuild, notifyHandle);
        }

        EmbedBuilder eb = new EmbedBuilder();

        if (guildService.getGuildByID(idGuild).isScheduleSubscribed()) {
            outputMsg = "Notifikasi dinonaktifkan!";
            notifyOff(subscriber.get(idGuild));
            eb.setColor(Color.RED);
        } else {
            outputMsg = "Notifikasi aktif! Schedule Anda akan dinotifikasikan jam 00:00 tiap harinya.";
            subscriber.replace(idGuild, notifyOn(message, idGuild));
            eb.setColor(Color.GREEN);
        }
        eb.setDescription(outputMsg);

        message.getChannel().sendMessage(eb.build()).queue();
        guildService.notifySchedule(idGuild);

    }
}
