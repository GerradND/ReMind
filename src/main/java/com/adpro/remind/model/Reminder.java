package com.adpro.remind.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reminder")
@Data
@NoArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name="taskID", nullable = false)
    private Task task;

    public Reminder(LocalDate date, LocalTime time){
        this.date = date;
        this.time = time;
    }
}
