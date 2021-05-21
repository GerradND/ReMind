package com.adpro.remind.service;

import com.adpro.remind.model.Schedule;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule);

    Schedule getScheduleByID(Integer idSchedule);

    Schedule updateSchedule(Integer idSchedule, Schedule schedule);

    void deleteSchedule(Integer idSchedule);

    Iterable<Schedule> getScheduleByDay(String day);

    Iterable<Schedule> getListSchedule();
}
