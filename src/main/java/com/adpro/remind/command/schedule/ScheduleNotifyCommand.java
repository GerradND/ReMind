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
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;


public class ScheduleNotifyCommand implements Command {
    private GuildService guildService;
    private ScheduleService scheduleService;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private HashMap<String, ScheduledFuture<?>> subscriber = new HashMap<>();
    private ScheduledFuture<?> notifyHandle;


    public ScheduleNotifyCommand(GuildService guildService, ScheduleService scheduleService){
        this.guildService = guildService;
        this.scheduleService = scheduleService;
    }

    public ScheduledFuture<?> notifyOn(Message message, String idGuild) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(0).withMinute(0).withSecond(5);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);

        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();

        final Runnable notifier = new Runnable() {
            public void run() {
                EmbedBuilder eb = new EmbedBuilder();
                String today = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
                Iterable<Schedule> listScheduleDay = scheduleService.getScheduleByDay(today.toUpperCase(), idGuild);
                eb.setTitle(":yellow_square: " + today);

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
                message.getChannel().sendMessage(eb.build()).queue();
            }
        };
//        this.notifyHandle = scheduler.scheduleAtFixedRate(notifier, 10, 10, SECONDS);
        this.notifyHandle = scheduler.scheduleAtFixedRate(notifier, initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS); // -> set sesuai timer
        return notifyHandle;

    }

    public void notifyOff(ScheduledFuture<?> notifyHandle) {
        notifyHandle.cancel(true);
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String idGuild = message.getGuild().getId();
        if(!subscriber.containsKey(idGuild)) {
            subscriber.put(idGuild, notifyHandle);
        }

        EmbedBuilder eb = new EmbedBuilder();

        if (guildService.getGuildByID(idGuild).isScheduleSubscribed()) {
            notifyOff(subscriber.get(idGuild));
            eb.setColor(Color.RED);
            eb.setDescription("Notifikasi dinonaktifkan!");
        } else {
            subscriber.replace(idGuild, notifyOn(message, idGuild));
            eb.setColor(Color.GREEN);
            eb.setDescription("Notifikasi aktif! Schedule Anda akan dinotifikasikan jam 00:00 tiap harinya.");
        }
        message.getChannel().sendMessage(eb.build()).queue();
        guildService.notifySchedule(idGuild);

    }
}
