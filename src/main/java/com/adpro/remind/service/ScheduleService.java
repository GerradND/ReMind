package com.adpro.remind.service;

import com.adpro.remind.model.Schedule;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule);

    Schedule getScheduleByID(Integer idSchedule);

    Schedule updateSchedule(Integer idSchedule, Schedule schedule);

    void deleteSchedule(Integer idSchedule);

    Iterable<Schedule> getScheduleByDay(String day);

    Iterable<Schedule> getListSchedule(String idGuild);
}
