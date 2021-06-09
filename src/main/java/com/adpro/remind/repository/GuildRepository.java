package com.adpro.remind.repository;

import com.adpro.remind.model.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends JpaRepository<Guild, String> {
    Guild findByIdGuild(String idGuild);
}
