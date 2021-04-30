package com.adpro.remind.model;

import java.time.LocalDateTime;

public class Schedule {
    private Integer idSchedule;
    private String title;
    private String day;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;

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
