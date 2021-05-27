package com.adpro.remind.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "guild")
@Data
@NoArgsConstructor
public class Guild {
    @Id
    @Column(name = "id_guild", updatable = false)
    private String idGuild;

    @OneToMany(targetEntity = Schedule.class, mappedBy = "guild", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Schedule> scheduleList;

    @OneToMany(targetEntity = Task.class, mappedBy = "guild", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Task> taskList;

    @OneToMany(targetEntity = TodoList.class, mappedBy = "guild", cascade = CascadeType.ALL)
    @JsonIgnore
    List<TodoList> todoList;

    public Guild(String idGuild){
        this.idGuild = idGuild;
    }
}
