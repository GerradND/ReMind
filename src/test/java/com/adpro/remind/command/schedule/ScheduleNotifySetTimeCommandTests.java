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

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ScheduleNotifySetTimeCommandTests {
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
    private ScheduleNotifySetTimeCommand scheduleNotifySetTimeCommand;

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
    public void testGetTimeMethod() {
        String time = "00:00";
        assertEquals(LocalTime.of(0,0), scheduleNotifySetTimeCommand.getTime(time));
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

        scheduleNotifySetTimeCommand.notificationMessage(message, "123", today.toString());

        assertTrue(scheduleNotifySetTimeCommand.getScheduleListDay().contains(schedule));
        assertEquals("Berikut adalah Schedule Anda untuk hari " + today.toString() + ":",
                scheduleNotifySetTimeCommand.getOutputMsg());
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

        scheduleNotifySetTimeCommand.notificationMessage(message, "123", today.toString());

        assertEquals("Schedule Anda kosong untuk hari " + today.toString() + " :smile:",
                scheduleNotifySetTimeCommand.getOutputMsg());
    }

    @Test
    public void testChangeNotifyTimeFirstInit() {
        String[] inputContent = {"-schedule", "notifyset", "09:00"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(guildService.getScheduleSubscriber()).thenReturn(subscriber);
        lenient().when(scheduleService.getScheduleByDay(today.toString(), "123")).thenReturn(scheduleListToday);

        lenient().when(guildService.getGuildByID("123")).thenReturn(guild);
        lenient().when(guildService.getNotifyTimeSchedule("123")).thenReturn(LocalTime.of(9,0));
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleNotifySetTimeCommand.getOutputMessage(message, inputContent);

        assertEquals("Notifikasi aktif! Schedule Anda akan dinotifikasikan jam 09:00 tiap harinya.",
                scheduleNotifySetTimeCommand.getOutputMsg());
    }

    @Test
    public void testChangeNotifyTime() {
        String[] inputContent = {"-schedule", "notifyset", "09:00"};

        subscriber.put("123", notifyHandle);

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(guildService.getScheduleSubscriber()).thenReturn(subscriber);
        lenient().when(scheduleService.getScheduleByDay(today.toString(), "123")).thenReturn(scheduleListToday);

        lenient().when(guildService.getGuildByID("123")).thenReturn(guild);
        lenient().when(guildService.getNotifyTimeSchedule("123")).thenReturn(LocalTime.of(9,0));
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleNotifySetTimeCommand.getOutputMessage(message, inputContent);

        assertEquals("Notifikasi aktif dan berhasil diubah menjadi jam 09:00 tiap harinya.",
                scheduleNotifySetTimeCommand.getOutputMsg());
    }
}
