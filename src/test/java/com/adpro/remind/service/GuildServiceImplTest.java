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
        dummy = guildService.getGuildByID("test2");
        assertNotEquals(dummy, guild);
    }

    @Test
    public void testGetGuildByIdSuccess() {
        Guild dummy = new Guild("test");
        lenient().when(guildRepository.findByIdGuild("123")).thenReturn(guild);
        Guild dummy2 = guildService.getGuildByID("123");
        assertNotEquals(dummy, dummy2);
    }

    @Test
    public void testDeleteGuildSuccess() {
        guildService.deleteGuild("123");
        Guild dummy = guildService.getGuildByID("123");
        assertEquals(null, dummy);
    }
}
