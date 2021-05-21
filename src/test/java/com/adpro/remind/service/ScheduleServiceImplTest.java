package com.adpro.remind.service;

<<<<<<< HEAD
import com.adpro.remind.controller.schedule.AddSchedule;
import com.adpro.remind.controller.schedule.ScheduleCommand;
import com.adpro.remind.model.Schedule;
=======
import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

<<<<<<< HEAD
import org.mockito.junit.jupiter.MockitoExtension;

=======
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;

>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTest {

<<<<<<< HEAD
    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private ScheduleCommand scheduleCommand;
    private Schedule schedule;

    public void setUp(){
        Schedule schedule = new Schedule("Adpro", AddSchedule.getDayOfWeek("Monday"),
                AddSchedule.getTime("08:00"), AddSchedule.getTime("10:00"), "Kelas pagi");
=======
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Schedule schedule;

    @BeforeEach
    public void setUp(){
        schedule = new Schedule("Adpro", DayOfWeek.MONDAY,
               LocalTime.of(8,0), LocalTime.of(10,0), "Kelas pagi");
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
    }

    @Test
    void testServiceCreateSchedule() {
        lenient().when(scheduleService.createSchedule(schedule)).thenReturn(schedule);
<<<<<<< HEAD
        lenient().when(scheduleCommand.getOutputMessage()).thenReturn("Schedule " + schedule.getTitle() + " berhasil ditambahkan!");
        String output = scheduleCommand.getOutputMessage();
        assertEquals(output, "Schedule Adpro berhasil ditambahkan!");
    }
=======
        assertEquals(scheduleService.createSchedule(schedule), schedule);
    }

    @Test
    void testServiceGetScheduleById() {
        lenient().when(scheduleRepository.findByIdSchedule(1)).thenReturn(schedule);
        Schedule resultSchedule = scheduleService.getScheduleByID(1);
        assertEquals(schedule.getIdSchedule(), resultSchedule.getIdSchedule());
    }

    @Test
    void testServiceUpdateSchedule() {
        scheduleService.createSchedule(schedule);
        DayOfWeek oldDay = schedule.getDay();

        schedule.setDay(DayOfWeek.WEDNESDAY);
        schedule.setStartTime(LocalTime.of(9,0));
        schedule.setEndTime(LocalTime.of(11,0));

        lenient().when(scheduleService.updateSchedule(schedule.getIdSchedule(), schedule)).thenReturn(schedule);
        lenient().when(scheduleRepository.findByIdSchedule(schedule.getIdSchedule())).thenReturn(schedule);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule.getIdSchedule(), schedule);

        assertNotEquals(updatedSchedule.getDay(), oldDay);
        assertEquals(updatedSchedule.getDay(), schedule.getDay());
    }

    @Test
    void testServiceGetListSchedule() {
        Iterable<Schedule> listSchedule = scheduleRepository.findAll();
        lenient().when(scheduleService.getListSchedule()).thenReturn(listSchedule);
        Iterable<Schedule> resultListSchedule = scheduleService.getListSchedule();
        assertIterableEquals(listSchedule, resultListSchedule);
    }

    @Test
    void testServiceGetScheduleByDay() {
        Iterable<Schedule> listSchedule = scheduleRepository.findByDay(DayOfWeek.MONDAY);
        lenient().when(scheduleService.getScheduleByDay("MONDAY")).thenReturn(listSchedule);
        Iterable<Schedule> resultListSchedule = scheduleService.getScheduleByDay("MONDAY");
        assertIterableEquals(listSchedule, resultListSchedule);
    }

    @Test
    void testServiceDeleteSchedule() {
        Integer idSchedule = schedule.getIdSchedule();
        scheduleService.deleteSchedule(idSchedule);
        lenient().when(scheduleService.getScheduleByID(idSchedule)).thenReturn(null);
        assertNull(scheduleService.getScheduleByID(idSchedule));
    }

>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
}
