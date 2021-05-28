package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.repository.GuildRepository;
import com.adpro.remind.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildServiceImpl implements GuildService{

    @Autowired
    private GuildRepository guildRepository;

    @Override
    public void createGuild(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        if (guild != null) {
            return;
        }
        else{
            guild = new Guild(idGuild);
            guildRepository.save(guild);
        }
    }

    @Override
    public Guild getGuildByID(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        return guild;
    }

    @Override
    public void deleteGuild(String idGuild) {
        Guild guild = guildRepository.findByIdGuild(idGuild);
        guildRepository.delete(guild);
    }
}
