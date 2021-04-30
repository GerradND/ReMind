package com.adpro.remind.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idSchedule;

    @Column(name = "title")
    private String title;

    @Column(name = "day")
    private String day;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "desc", columnDefinition = "text")
    private String description;

    // implement ManyToOne User

    public Schedule(String title, String day, LocalDateTime startTime, LocalDateTime endTime, String description){
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;

    }

    public Integer getIdSchedule() {
        return idSchedule;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setIdSchedule(Integer idSchedule) {
        this.idSchedule = idSchedule;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
