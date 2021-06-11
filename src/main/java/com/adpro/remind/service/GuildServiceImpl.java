package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.repository.GuildRepository;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildServiceImpl implements GuildService {

    private GuildRepository guildRepository;
    private HashMap<String, ScheduledFuture<?>> scheduleSubscriber = new HashMap<>();

    @Autowired
    public GuildServiceImpl(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    @Override
    public void createGuild(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        if (guild != null) {
            return;
        } else {
            guild = new Guild(idGuild);
            guildRepository.save(guild);
        }
    }

    @Override
    public Guild getGuildById(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        return guild;
    }

    @Override
    public void deleteGuild(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        guildRepository.delete(guild);
    }

    @Override
    public Guild notifySchedule(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        guild.setScheduleSubscribed(!guild.isScheduleSubscribed());
        guildRepository.save(guild);
        return guild;
    }

    @Override
    public LocalTime getNotifyTimeSchedule(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        return guild.getScheduleNotificationTime();
    }

    @Override
    public void setNotifyTimeSchedule(String idGuild, LocalTime time) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        guild.setScheduleNotificationTime(time);
        guildRepository.save(guild);
    }

    public HashMap<String, ScheduledFuture<?>> getScheduleSubscriber() {
        return scheduleSubscriber;
    }
}
