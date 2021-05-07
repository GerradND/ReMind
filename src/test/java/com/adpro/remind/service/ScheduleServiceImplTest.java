package com.adpro.remind.service;

import com.adpro.remind.controller.schedule.AddSchedule;
import com.adpro.remind.controller.schedule.ScheduleCommand;
import com.adpro.remind.model.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private ScheduleCommand scheduleCommand;
    private Schedule schedule;

    public void setUp(){
        Schedule schedule = new Schedule("Adpro", AddSchedule.getDayOfWeek("Monday"),
                AddSchedule.getTime("08:00"), AddSchedule.getTime("10:00"), "Kelas pagi");
    }

    @Test
    void testServiceCreateSchedule() {
        lenient().when(scheduleService.createSchedule(schedule)).thenReturn(schedule);
        lenient().when(scheduleCommand.getOutputMessage()).thenReturn("Schedule " + schedule.getTitle() + " berhasil ditambahkan!");
        String output = scheduleCommand.getOutputMessage();
        assertEquals(output, "Schedule Adpro berhasil ditambahkan!");
    }
}
