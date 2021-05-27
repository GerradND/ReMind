package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

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
        return scheduleRepository.findByDay(DayOfWeek.valueOf(day));
    }

    @Override
    public Iterable<Schedule> getListSchedule(String idGuild) {
        Guild guild = guildRepository.findById(idGuild).orElse(null);
        if (guild == null){
            return null;
        }
        return scheduleRepository.findByGuild(guild);
    }

}
