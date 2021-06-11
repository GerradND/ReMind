package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;

public interface GuildService {
    void createGuild(String idGuild);

    Guild getGuildById(String idGuild);

    void deleteGuild(String idGuild);

    Guild notifySchedule(String idGuild);

    LocalTime getNotifyTimeSchedule(String idGuild);

    void setNotifyTimeSchedule(String idGuild, LocalTime time);

    HashMap<String, ScheduledFuture<?>> getScheduleSubscriber();
}
