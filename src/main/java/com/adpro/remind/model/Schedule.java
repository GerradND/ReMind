package com.adpro.remind.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idSchedule;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "day")
    private String day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    // implement ManyToOne User
    public Schedule(String title, String day, LocalTime startTime, LocalTime endTime, String description){
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
}
