package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
@Setter
@Getter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_task")
    private Integer idTask;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @OneToMany(targetEntity = Reminder.class, mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reminder> reminders = new HashSet<>();

    public Task(String name, LocalDate date, LocalTime time){
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setReminder(Reminder reminder){
        reminders.add(reminder);
    }

    public String getAllReminders(){
        String listReminder = "Reminder yang telah dipasang: \n";
        if(reminders.size() > 0) {
            for (Reminder reminder : reminders) {
                listReminder += "- " + reminder.getDate() + " " + reminder.getTime() + "\n";
            }
        }
        return listReminder;
    }


}