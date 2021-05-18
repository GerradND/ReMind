package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idSchedule;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "day")
    private DayOfWeek day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "description", columnDefinition = "text")
    private String description;

//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "id_user")
//    private User user;
//
//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "id_guild")
//    private Guild guild;

    public Schedule(String title, DayOfWeek day, LocalTime startTime, LocalTime endTime, String description){
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
}
