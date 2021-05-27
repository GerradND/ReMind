package com.adpro.remind.service;

import com.adpro.remind.model.Guild;

public interface GuildService {
    void createGuild(String idGuild);
    Guild getGuildByID(String idGuild);
    void deleteGuild(String idGuild);
}
