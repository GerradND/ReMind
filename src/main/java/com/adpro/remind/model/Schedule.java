package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule")
@Setter
@Getter
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_guild")
    private Guild guild;

    public Schedule(String title, DayOfWeek day, LocalTime startTime, LocalTime endTime, String description) {
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

}
