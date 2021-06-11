package com.adpro.remind.command.schedule;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
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
import org.springframework.util.Assert;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ScheduleUpdateDescriptionCommandTests {
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
    private ScheduleUpdateDescriptionCommand scheduleUpdateDescriptionCommand;

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
    public void testUpdateScheduleFormDescriptionMethod() {
        String[] inputContent = {"-schedule", "updatedesc", "1", "Advanced_Programming", "Kelas", "pagi", "banget"};
        assertEquals("Kelas pagi banget", scheduleUpdateDescriptionCommand.formDescription(inputContent, 4));

    }

    @Test
    public void testScheduleUpdateSuccessOutput() {
        String[] inputContent = {"-schedule", "updatedesc", "1", "Advanced_Programming", "Kelas", "pagi", "banget"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(1)).thenReturn(schedule);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleUpdateDescriptionCommand.getOutputMessage(message, inputContent);

        assertEquals(":pencil2: Keterangan schedule \"Adpro\" berhasil diubah!",
                scheduleUpdateDescriptionCommand.getOutputMsg());
    }

    @Test
    public void testScheduleUpdateNotFoundOutput() {
        String[] inputContent = {"-schedule", "updatedesc", "2", "Lol", "get", "rekt", "banget"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(2)).thenReturn(null);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleUpdateDescriptionCommand.getOutputMessage(message, inputContent);

        assertEquals("Schedule dengan ID: 2 tidak ditemukan!",
                scheduleUpdateDescriptionCommand.getOutputMsg());
    }

    @Test
    public void testScheduleUpdateWrongIDFormatOutput() {
        String[] inputContent = {"-schedule", "delete", "lol"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(anyInt())).thenThrow(NumberFormatException.class);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleUpdateDescriptionCommand.getOutputMessage(message, inputContent);

        assertEquals("Tolong masukan ID yang valid.",
                scheduleUpdateDescriptionCommand.getOutputMsg());
    }

}
