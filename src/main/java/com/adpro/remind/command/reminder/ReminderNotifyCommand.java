package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.adpro.remind.service.GuildService;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Value;

import javax.security.auth.login.LoginException;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ReminderNotifyCommand implements Command {
    private JDA jda;
    private GuildService guildService;
    private TaskService taskService;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private HashMap<String, ScheduledFuture<?>> subscriber = new HashMap<>();
    private ScheduledFuture<?> notifyHandle;

//    @Value("${token}")
    private String token = "ODM2MDg3NDY0NDUyMDMwNDY0.YIY5IQ.nLEk_v3_ArzDmuFCKLF8ChImhN0";

    public ReminderNotifyCommand(GuildService guildService, TaskService taskService) throws LoginException {
        this.guildService = guildService;
        this.taskService = taskService;
        this.jda = JDABuilder.createDefault(token).build();
        notifyHandle = notifyOn();
    }

    public ScheduledFuture<?> notifyOn() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withSecond(5);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusMinutes(1);

        final Runnable notifier = new Runnable() {
            public void run() {
                LocalDateTime dateTimeNow = LocalDateTime.now();
                List<Reminder> reminders = taskService.findAllReminder();
                for (Reminder reminder : reminders) {
                    LocalDate date = reminder.getDate();
                    LocalTime time = reminder.getTime();
                    LocalDateTime dateTime = LocalDateTime.of(date, time);

                    System.out.println("Masuk run: " + dateTimeNow + " -- " + dateTime);
                    if (dateTime.isBefore(dateTimeNow)) {
                        System.out.println("output reminder");
                        Task task = reminder.getTask();
                        Integer id = reminder.getIdReminder();
                        String idChannel = reminder.getIdChannel();

                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Reminder!!!");
                        eb.addField("Task Name", task.getName(), false);
                        eb.appendDescription("Pesan ini adalah reminder untuk " + task.getName() + " pada "
                                + task.getDate() + " " + task.getTime());

                        TextChannel textChannel = jda.getTextChannelById(idChannel);

                        textChannel.sendMessage(eb.build()).queue();

                        taskService.deleteReminder(id);
                    }
                }
            }
        };
        this.notifyHandle = scheduler.scheduleAtFixedRate(notifier, 60, 60, SECONDS);
        return notifyHandle;

    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {

    }
}
