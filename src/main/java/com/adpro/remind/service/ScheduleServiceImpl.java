package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ScheduleRepository;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;
    private GuildRepository guildRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, GuildRepository guildRepository) {
        this.scheduleRepository = scheduleRepository;
        this.guildRepository = guildRepository;
    }

    @Override
    public Schedule createSchedule(Schedule schedule, String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        schedule.setGuild(guild);
        guild.getScheduleList().add(schedule);

        scheduleRepository.save(schedule);
        guildRepository.save(guild);
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
    public void deleteSchedule(Integer idSchedule, String idGuild) {
        scheduleRepository.deleteById(idSchedule);
    }

    @Override
    public List<Schedule> getScheduleByDay(String day, String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        return scheduleRepository.findByDayAndGuild(DayOfWeek.valueOf(day.toUpperCase()), guild);
    }

    @Override
    public List<Schedule> getListSchedule(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        return scheduleRepository.findAllByGuild(guild);
    }

}
