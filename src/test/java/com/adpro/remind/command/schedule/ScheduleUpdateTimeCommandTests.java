package com.adpro.remind.command.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
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

@ExtendWith(MockitoExtension.class)
public class ScheduleUpdateTimeCommandTests {
    @Mock
    private net.dv8tion.jda.api.entities.Guild guildDC;

    @Mock
    private Message message;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleUpdateTimeCommand scheduleUpdateTimeCommand;

    private Schedule schedule;
    private Guild guild;

    @BeforeEach
    public void setUp() {
        schedule = new Schedule();
        schedule.setTitle("Adpro");
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setStartTime(LocalTime.of(8,0));
        schedule.setEndTime(LocalTime.of(10,0));
        schedule.setDescription("Kelas pagi");
        guild = new Guild("123");
        guild.setScheduleList(new ArrayList<>());
        schedule.setGuild(guild);
    }

    @Test
    public void testGetDayOfWeekMethod() {
        String day = "Monday";
        assertEquals(DayOfWeek.MONDAY, scheduleUpdateTimeCommand.getDayOfWeek(day));
    }

    @Test
    public void testGetTimeMethod() {
        String time = "00:00";
        assertEquals(LocalTime.of(0,0), scheduleUpdateTimeCommand.getTime(time));
    }

    @Test
    public void testScheduleDeleteSuccessOutput() {
        String[] inputContent = {"-schedule", "update", "1", "Tuesday", "09:00", "10:00"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(1)).thenReturn(schedule);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleUpdateTimeCommand.getOutputMessage(message, inputContent);

        assertEquals(":clock2: Waktu schedule \"Adpro\" berhasil diubah!",
                scheduleUpdateTimeCommand.getOutputMsg());
    }

    @Test
    public void testScheduleDeleteNotFoundOutput() {
        String[] inputContent = {"-schedule", "update", "2", "Tuesday", "09:00", "10:00"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(2)).thenReturn(null);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleUpdateTimeCommand.getOutputMessage(message, inputContent);

        assertEquals("Schedule dengan ID: 2 tidak ditemukan!",
                scheduleUpdateTimeCommand.getOutputMsg());
    }

    @Test
    public void testScheduleDeleteWrongIDFormatOutput() {
        String[] inputContent = {"-schedule", "update", "lol", "Tuesday", "09:00", "10:00"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(anyInt())).thenThrow(NumberFormatException.class);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleUpdateTimeCommand.getOutputMessage(message, inputContent);

        assertEquals("Tolong masukan ID yang valid.",
                scheduleUpdateTimeCommand.getOutputMsg());
    }
}
