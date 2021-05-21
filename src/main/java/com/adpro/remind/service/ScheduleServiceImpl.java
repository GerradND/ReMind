package com.adpro.remind.service;

import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD

public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

=======
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
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
<<<<<<< HEAD
       Schedule schedule = scheduleRepository.findByIdSchedule(idSchedule);
       scheduleRepository.delete(schedule);
=======
        Schedule schedule = scheduleRepository.findByIdSchedule(idSchedule);
        scheduleRepository.delete(schedule);
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
    }

    @Override
    public Iterable<Schedule> getScheduleByDay(String day) {
<<<<<<< HEAD
        return scheduleRepository.findByDay(day);
=======
        return scheduleRepository.findByDay(DayOfWeek.valueOf(day));
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
    }

    @Override
    public Iterable<Schedule> getListSchedule() {
        return scheduleRepository.findAll();
    }
<<<<<<< HEAD
}
=======

}
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
