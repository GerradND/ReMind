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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTests {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private GuildRepository guildRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Schedule schedule;
    private Guild guild;

    @BeforeEach
    public void setUp(){
        schedule = new Schedule("Adpro", DayOfWeek.MONDAY,
               LocalTime.of(8,0), LocalTime.of(10,0), "Kelas pagi");
        guild = new Guild("123");
        guild.setScheduleList(new ArrayList<>());

    }

    @Test
    void testServiceCreateSchedule() {
        lenient().when(guildRepository.findByIdGuild("123")).thenReturn(guild);
        lenient().when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Schedule newSchedule = scheduleService.createSchedule(schedule, "123");

        assertEquals(schedule, newSchedule);
    }

    @Test
    void testServiceGetScheduleById() {
        lenient().when(scheduleRepository.findByIdSchedule(1)).thenReturn(schedule);
        Schedule resultSchedule = scheduleService.getScheduleByID(1);
        assertEquals(schedule.getIdSchedule(), resultSchedule.getIdSchedule());
    }

    @Test
    void testServiceUpdateSchedule() {
        lenient().when(guildRepository.findByIdGuild("123")).thenReturn(guild);
        scheduleService.createSchedule(schedule, "123");
        DayOfWeek oldDay = schedule.getDay();

        schedule.setDay(DayOfWeek.WEDNESDAY);
        schedule.setStartTime(LocalTime.of(9,0));
        schedule.setEndTime(LocalTime.of(11,0));

        lenient().when(scheduleRepository.findByIdSchedule(1)).thenReturn(schedule);

        Schedule updatedSchedule = scheduleService.updateSchedule(1, schedule);

        assertNotEquals(updatedSchedule.getDay(), oldDay);
        assertEquals(updatedSchedule.getDay(), schedule.getDay());
    }

    @Test
    void testServiceGetListSchedule() {
        lenient().when(scheduleRepository.save(schedule)).thenReturn(schedule);
        lenient().when(guildRepository.findByIdGuild("123")).thenReturn(guild);
        lenient().when(scheduleRepository.findAllByGuild(guild)).thenReturn(guild.getScheduleList());

        scheduleService.createSchedule(schedule, "123");
        Iterable<Schedule> resultListSchedule = scheduleService.getListSchedule("123");

        assertIterableEquals(guild.getScheduleList(), resultListSchedule);
    }

    @Test
    void testServiceGetScheduleByDay() {
        lenient().when(scheduleRepository.save(schedule)).thenReturn(schedule);
        lenient().when(guildRepository.findByIdGuild("123")).thenReturn(guild);

        DayOfWeek monday = DayOfWeek.MONDAY;
        Map<DayOfWeek, List<Schedule>> guildSchedulePerDay = new HashMap<>();

        scheduleService.createSchedule(schedule, "123");
        guildSchedulePerDay.put(monday, guild.getScheduleList());

        lenient().when(scheduleRepository.findByDayAndGuild(monday, guild)).thenReturn(guildSchedulePerDay.get(monday));
        Iterable<Schedule> resultListSchedule = scheduleService.getScheduleByDay("MONDAY", "123");

        assertEquals(1, resultListSchedule.spliterator().getExactSizeIfKnown());
    }

    @Test
    void testServiceDeleteSchedule() {
        Integer idSchedule = schedule.getIdSchedule();
        scheduleService.deleteSchedule(idSchedule);
        lenient().when(scheduleService.getScheduleByID(idSchedule)).thenReturn(null);
        assertNull(scheduleService.getScheduleByID(idSchedule));
    }

}
