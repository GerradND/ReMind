package com.adpro.remind.command.schedule;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleServiceImpl;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ScheduleAddCommandTests {
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
    private ScheduleAddCommand scheduleAddCommand;

    private Guild guild;
    private Schedule schedule;

    @BeforeEach
    public void setUp(){
        schedule = new Schedule();
        schedule.setIdSchedule(1);
        schedule.setTitle("Adpro");
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setStartTime(LocalTime.of(8,0));
        schedule.setEndTime(LocalTime.of(10,0));
        schedule.setDescription("Kelas pagi");
        guild = new Guild("123");
        guild.setScheduleList(new ArrayList<>());
    }

    @Test
    public void testFormDescriptionMethod() {
        String[] inputContent = {"-schedule", "add", "test", "test", "00:00", "01:00", "This", "is", "desc"};
        assertEquals("This is desc", scheduleAddCommand.formDescription(inputContent));
    }

    @Test
    public void testFormDescriptionMethodIfEmpty() {
        String[] inputContent = {"-schedule", "add", "test", "test", "00:00", "01:00"};
        assertEquals("-", scheduleAddCommand.formDescription(inputContent));
    }

    @Test
    public void testGetDayOfWeekMethod() {
        String day = "Monday";
        assertEquals(DayOfWeek.MONDAY, scheduleAddCommand.getDayOfWeek(day));
    }

    @Test
    public void testGetTimeMethod() {
        String time = "00:00";
        assertEquals(LocalTime.of(0,0), scheduleAddCommand.getTime(time));
    }

    @Test
    public void testScheduleAddOutputMessage() {
        String[] inputContent = {"-schedule", "add", "Adpro", "Monday", "08:00", "10:00", "Kelas", "pagi"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");
        lenient().when(scheduleService.createSchedule(any(Schedule.class), any(String.class))).thenReturn(schedule);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();


        scheduleAddCommand.getOutputMessage(message, inputContent);

        assertEquals(":white_check_mark: Schedule \"Adpro\" berhasil ditambahkan!", scheduleAddCommand.getOutputMsg());
    }

    @Test
    public void testScheduleAddOutputMessageWrongParam() {
        String[] inputContent = {"-schedule", "add", "Adpro", "lol", "08:00", "10:00", "Kelas"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");
        lenient().when(scheduleService.createSchedule(any(Schedule.class), any(String.class))).thenReturn(null);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleAddCommand.getOutputMessage(message, inputContent);

        assertEquals("Penambahan schedule gagal/terdapat kesalahan parameter. Silahkan coba lagi.", scheduleAddCommand.getOutputMsg());
    }
}
