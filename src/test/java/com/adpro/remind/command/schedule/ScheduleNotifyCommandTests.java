package com.adpro.remind.command.schedule;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.GuildServiceImpl;
import com.adpro.remind.service.ScheduleServiceImpl;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleNotifyCommandTests {
    @Mock
    private net.dv8tion.jda.api.entities.Guild guildDC;

    @Mock
    private Message message;

    @Mock
    private ScheduledFuture<?> notifyHandle;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @Mock
    private GuildServiceImpl guildService;

    @Mock
    private ScheduleServiceImpl scheduleService;

    @InjectMocks
    private ScheduleNotifyCommand scheduleNotifyCommand;

    private Guild guild;
    private Schedule schedule;
    private DayOfWeek today;
    private HashMap<String, ScheduledFuture<?>> subscriber;
    private List<Schedule> scheduleListToday = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        today = LocalDateTime.now().getDayOfWeek();
        schedule = new Schedule();
        schedule.setTitle("Adpro");
        schedule.setDay(today);
        schedule.setStartTime(LocalTime.of(8,0));
        schedule.setEndTime(LocalTime.of(10,0));
        schedule.setDescription("Kelas pagi");
        guild = new Guild("123");
        guild.setScheduleList(new ArrayList<>());
        subscriber = new HashMap<>();
        schedule.setGuild(guild);
        scheduleListToday.add(schedule);

    }

    @Test
    public void testNotificationMessageMethod() {
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(guildService.getScheduleSubscriber()).thenReturn(subscriber);
        lenient().when(scheduleService.getScheduleByDay(today.toString(), "123")).thenReturn(scheduleListToday);

        lenient().when(guildService.getGuildByID("123")).thenReturn(guild);
        lenient().when(guildService.getNotifyTimeSchedule("123")).thenReturn(LocalTime.now());
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleNotifyCommand.notificationMessage(message, "123", today.toString());

        assertTrue(scheduleNotifyCommand.getScheduleListDay().contains(schedule));
        assertEquals("Berikut adalah Schedule Anda untuk hari " + today.toString() + ":",
                scheduleNotifyCommand.getOutputMsg());
    }

    @Test
    public void testNotificationMessageMethodWhenScheduleEmpty() {
        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(guildService.getScheduleSubscriber()).thenReturn(subscriber);
        lenient().when(scheduleService.getScheduleByDay(today.toString(), "123")).thenReturn(new ArrayList<>());

        lenient().when(guildService.getGuildByID("123")).thenReturn(guild);
        lenient().when(guildService.getNotifyTimeSchedule("123")).thenReturn(LocalTime.now());
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleNotifyCommand.notificationMessage(message, "123", today.toString());

        assertEquals("Schedule Anda kosong untuk hari " + today.toString() + " :smile:",
                scheduleNotifyCommand.getOutputMsg());
    }

    @Test
    public void testNotifyOnMethodOutput() {
        String[] inputContent = {"-schedule", "notify"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(guildService.getScheduleSubscriber()).thenReturn(subscriber);
        lenient().when(scheduleService.getScheduleByDay(today.toString(), "123")).thenReturn(scheduleListToday);

        lenient().when(guildService.getGuildByID("123")).thenReturn(guild);
        lenient().when(guildService.getNotifyTimeSchedule("123")).thenReturn(LocalTime.now());
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleNotifyCommand.getOutputMessage(message, inputContent);

        assertEquals("Notifikasi aktif! Schedule Anda akan dinotifikasikan jam 00:00 tiap harinya.",
                scheduleNotifyCommand.getOutputMsg());
    }

    @Test
    public void testNotifyOffMethodOutput() {
        String[] inputContent = {"-schedule", "notify"};

        guild.setScheduleSubscribed(true);
        subscriber.put("123", notifyHandle);

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(guildService.getScheduleSubscriber()).thenReturn(subscriber);
        lenient().when(scheduleService.getScheduleByDay(today.toString(), "123")).thenReturn(scheduleListToday);

        lenient().when(guildService.getGuildByID("123")).thenReturn(guild);
        lenient().when(guildService.getNotifyTimeSchedule("123")).thenReturn(LocalTime.now());
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleNotifyCommand.getOutputMessage(message, inputContent);

        assertEquals("Notifikasi dinonaktifkan!",
                scheduleNotifyCommand.getOutputMsg());
    }
}
