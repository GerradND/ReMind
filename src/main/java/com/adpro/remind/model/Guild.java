package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import discord4j.common.util.Snowflake;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "guild")
@Data
public class Guild {
    @Id @Column(name = "id_guild")
    private Snowflake idGuild;

    @Column(name = "guild_name")
    private String guildName;

    @JsonManagedReference
    @OneToMany(mappedBy = "guild")
    private List<Schedule> scheduleList;
}
