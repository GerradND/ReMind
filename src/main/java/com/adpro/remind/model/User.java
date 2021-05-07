package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import discord4j.common.util.Snowflake;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id @Column(name = "id_user")
    private Snowflake idUser;

    @Column(name = "username")
    private String username;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Schedule> scheduleList;
}
