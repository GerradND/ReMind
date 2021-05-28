package com.adpro.remind.service;

import com.adpro.remind.model.Schedule;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule, String idGuild);

    Schedule getScheduleByID(Integer idSchedule);

    Schedule updateSchedule(Integer idSchedule, Schedule schedule);

    void deleteSchedule(Integer idSchedule);

    Iterable<Schedule> getScheduleByDay(String day, String idGuild);

    Iterable<Schedule> getListSchedule(String idGuild);
}
