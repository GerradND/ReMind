package com.adpro.remind.service;

import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule createSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public Schedule getScheduleByID(Integer idSchedule) {
        return scheduleRepository.findByIdSchedule(idSchedule);
    }

    @Override
    public Schedule updateSchedule(Integer idSchedule, Schedule schedule) {
        schedule.setIdSchedule(idSchedule);
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public void deleteSchedule(Integer idSchedule) {
       Schedule schedule = scheduleRepository.findByIdSchedule(idSchedule);
       scheduleRepository.delete(schedule);
    }

    @Override
    public Iterable<Schedule> getScheduleByDay(String day) {
        return scheduleRepository.findByDay(day);
    }

    @Override
    public Iterable<Schedule> getListSchedule() {
        return scheduleRepository.findAll();
    }
}
