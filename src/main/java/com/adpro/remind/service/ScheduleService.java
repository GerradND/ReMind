package com.adpro.remind.service;

import com.adpro.remind.model.Schedule;
import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule, String idGuild);

    Schedule getScheduleByID(Integer idSchedule);

    Schedule updateSchedule(Integer idSchedule, Schedule schedule);

    void deleteSchedule(Integer idSchedule, String idGuild);

    List<Schedule> getScheduleByDay(String day, String idGuild);

    List<Schedule> getListSchedule(String idGuild);
}
