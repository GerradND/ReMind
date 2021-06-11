package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reminder")
@Setter
@Getter
@NoArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer idReminder;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name="id_channel")
    private String idChannel;

    @ManyToOne
    @JoinColumn(name="id_task")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;

    public Reminder(LocalDate date, LocalTime time, String idChannel) {
        this.date = date;
        this.time = time;
        this.idChannel = idChannel;
    }
}
