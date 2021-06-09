package com.adpro.remind.command.schedule;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ScheduleRepository;
import com.adpro.remind.service.ScheduleServiceImpl;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.GuildManager;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.*;
import net.dv8tion.jda.api.requests.restaction.order.CategoryOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;
import net.dv8tion.jda.api.requests.restaction.order.RoleOrderAction;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import net.dv8tion.jda.api.utils.cache.MemberCacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import net.dv8tion.jda.api.utils.cache.SortedSnowflakeCacheView;
import net.dv8tion.jda.api.utils.concurrent.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleDeleteCommandTests {
    @Mock
    private net.dv8tion.jda.api.entities.Guild guildDC;

    @Mock
    private Message message;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private MessageAction messageAction;

    @Mock
    private MessageEmbed messageEmbed;

    @Mock
    private ScheduleServiceImpl scheduleService;

    @InjectMocks
    private ScheduleDeleteCommand scheduleDeleteCommand;

    private Schedule schedule;
    private Guild guild;

    @BeforeEach
    public void setUp(){
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
    public void testScheduleDeleteSuccessOutput() {
        String[] inputContent = {"-schedule", "delete", "1"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(1)).thenReturn(schedule);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleDeleteCommand.getOutputMessage(message, inputContent);

        assertEquals(":negative_squared_cross_mark: Schedule " + schedule.getTitle() + " berhasil dihapus!",
                scheduleDeleteCommand.getOutputMsg());
    }

    @Test
    public void testScheduleDeleteNotFoundOutput() {
        String[] inputContent = {"-schedule", "delete", "2"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(2)).thenReturn(null);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleDeleteCommand.getOutputMessage(message, inputContent);

        assertEquals(":warning: Schedule tidak ditemukan/ID yang Anda masukan salah. Silahkan coba lagi!",
                scheduleDeleteCommand.getOutputMsg());
    }

    @Test
    public void testScheduleDeleteWrongIDFormatOutput() {
        String[] inputContent = {"-schedule", "delete", "lol"};

        lenient().when(message.getGuild()).thenReturn(guildDC);
        lenient().when(guildDC.getId()).thenReturn("123");

        lenient().when(scheduleService.getScheduleByID(anyInt())).thenThrow(NumberFormatException.class);
        lenient().when(message.getChannel()).thenReturn(messageChannel);

        lenient().when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
        doNothing().when(messageAction).queue();

        scheduleDeleteCommand.getOutputMessage(message, inputContent);

        assertEquals("Tolong masukan ID yang valid.",
                scheduleDeleteCommand.getOutputMsg());
    }

}
