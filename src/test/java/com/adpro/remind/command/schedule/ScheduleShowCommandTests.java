package com.adpro.remind.command.schedule;

import com.adpro.remind.model.Schedule;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ScheduleShowCommandTests {
    @Mock
    private net.dv8tion.jda.api.entities.Guild guildDC;

    @Mock
    private Message message;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @Mock
    private ScheduleServiceImpl scheduleService;

    @InjectMocks
    private ScheduleShowCommand scheduleShowCommand;

    private Schedule schedule;
    private List<Schedule> scheduleList = new ArrayList<>();
    private List<Schedule> scheduleListMonday = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        schedule = new Schedule("Adpro", DayOfWeek.MONDAY, LocalTime.of(8,0), LocalTime.of(10,0), "Kelas pagi");

        Schedule schedule2 = new Schedule();
        schedule2.setTitle("Adpro2");
        schedule2.setDay(DayOfWeek.TUESDAY);
        schedule2.setStartTime(LocalTime.of(8,0));
        schedule2.setEndTime(LocalTime.of(10,0));
        schedule2.setDescription("Kelas pagi");

        Schedule schedule3 = new Schedule();
        schedule3.setTitle("Adpro2");
        schedule3.setDay(DayOfWeek.WEDNESDAY);
        schedule3.setStartTime(LocalTime.of(8,0));
        schedule3.setEndTime(LocalTime.of(10,0));
        schedule3.setDescription("Kelas pagi");

        scheduleList.add(schedule);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);

        scheduleListMonday.add(schedule);
    }

    @Test
    public void testStringContainsItemFromListMethod() {
        String[] test = {"haha", "hihi", "hoho"};
        assertTrue(scheduleShowCommand.stringContainsItemFromList("haha", test));
    }

    @Test
    public void testIsIntByExceptionMethod() {
        assertTrue(scheduleShowCommand.isIntByException("5"));
    }

    @Test
    public void testScheduleShowAllList() {
        String[] inputContent = {"-schedule", "show", "all"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getListSchedule("123")).thenReturn(scheduleList);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertEquals(scheduleList, scheduleShowCommand.getScheduleList());
    }

    @Test
    public void testScheduleShowAllOutputWhenListEmpty() {
        String[] inputContent = {"-schedule", "show", "all"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("321");

        lenient().when(scheduleService.getListSchedule("321")).thenReturn(new ArrayList<>());
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertTrue(scheduleShowCommand.getScheduleList().isEmpty());
        assertEquals("Schedule Anda masih kosong!", scheduleShowCommand.getOutputMsg());
    }

    @Test
    public void testScheduleShowByDayList() {
        String[] inputContent = {"-schedule", "show", "Monday"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByDay("Monday", "123")).thenReturn(scheduleListMonday);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertEquals(scheduleListMonday, scheduleShowCommand.getScheduleList());
    }

    @Test
    public void testScheduleShowByDayOutputWhenListEmpty() {
        String[] inputContent = {"-schedule", "show", "Monday"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("321");

        lenient().when(scheduleService.getScheduleByDay("Monday", "321")).thenReturn(new ArrayList<>());
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertTrue(scheduleShowCommand.getScheduleList().isEmpty());
        assertEquals("Schedule Anda kosong untuk hari tersebut :smile:", scheduleShowCommand.getOutputMsg());
    }

    @Test
    public void testScheduleShowDetailOutput() {
        String[] inputContent = {"-schedule", "show", "1"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(1)).thenReturn(schedule);

        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();


        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertEquals(":yellow_circle: \"Adpro\"", scheduleShowCommand.getOutputMsg());

    }

    @Test
    public void testScheduleShowDetailOutputWrongID() {
        String[] inputContent = {"-schedule", "show", "2"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(2)).thenReturn(null);

        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();


        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertEquals("Schedule dengan ID: 2 tidak dapat ditemukan!", scheduleShowCommand.getOutputMsg());

    }

    @Test
    public void testWrongScheduleShowCommandOutput() {
        String[] inputContent = {"-schedule", "show", "test"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();


        scheduleShowCommand.getOutputMessage(message, inputContent);
        assertEquals("Command 'Show' yang Anda masukan salah! Silahkan coba lagi.", scheduleShowCommand.getOutputMsg());

    }
}
