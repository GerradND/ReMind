package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuildServiceImplTest {

    @Mock
    private GuildRepository guildRepository;

    @InjectMocks
    private GuildServiceImpl guildService;

    private Guild guild;

    @BeforeEach
    public void setUp(){
        guild = new Guild("123");
    }


    @Test
    public void testServiceCreateGuildSuccess() {
        Guild dummy;
        guildService.createGuild("test2");
        dummy = guildService.getGuildById("test2");
        assertNotEquals(dummy, guild);
    }

    @Test
    public void testGetGuildByIdSuccess() {
        Guild dummy = new Guild("test");
        lenient().when(guildRepository.findByIdGuild("123")).thenReturn(guild);
        Guild dummy2 = guildService.getGuildById("123");
        assertNotEquals(dummy, dummy2);
    }

    @Test
    public void testDeleteGuildSuccess() {
        guildService.deleteGuild("123");
        Guild dummy = guildService.getGuildById("123");
        assertEquals(null, dummy);
    }

    @Test
    public void testNotifySchedule() {
        Guild dummy = new Guild("test");
        lenient().when(guildRepository.findByIdGuild("test")).thenReturn(dummy);
        Guild dummy2 = guildService.getGuildById("test");
        dummy2 = guildService.notifySchedule("test");
        dummy2.setScheduleSubscribed(!dummy2.isScheduleSubscribed());
        assertEquals(false  , dummy2.isScheduleSubscribed());
    }

    @Test
    public void testGetNotifyTimeSchedule() {
        Guild dummy = new Guild("test");
        LocalTime time1 = LocalTime.parse("03:18:23");
        lenient().when(guildRepository.findByIdGuild("test")).thenReturn(dummy);
        Guild dummy2 = guildService.getGuildById("test");
        LocalTime time2 = guildService.getNotifyTimeSchedule("test");
        assertNotEquals(null, time2);
    }

    @Test
    public void testSetNotifyTimeSchedule() {
        Guild dummy = new Guild("test");
        LocalTime time1 = LocalTime.parse("03:18:23");
        lenient().when(guildRepository.findByIdGuild("test")).thenReturn(dummy);
        Guild dummy2 = guildService.getGuildById("test");
        dummy2.setScheduleNotificationTime(time1);
        guildService.setNotifyTimeSchedule("test", time1);
        assertEquals(time1, dummy2.getScheduleNotificationTime());
    }
    @Test
    public void testGetScheduleSubscriber() {
        Guild dummy = new Guild("test");
        lenient().when(guildRepository.findByIdGuild("test")).thenReturn(dummy);
        Guild dummy2 = guildService.getGuildById("test");
        HashMap<String, ScheduledFuture<?>> dummy3 = guildService.getScheduleSubscriber();
        assertEquals(dummy3, guildService.getScheduleSubscriber());
    }
}
