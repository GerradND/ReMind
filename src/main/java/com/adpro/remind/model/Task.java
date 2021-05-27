package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @JoinColumn(name = "id_guild")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Guild guild;

    public Task(String name, LocalDate date, LocalTime time, Guild guild){
        this.name = name;
        this.date = date;
        this.time = time;
        this.guild = guild;
    }

    public void setReminder(Reminder reminder){
        reminders.add(reminder);
    }

    public String getAllReminders(){
        String listReminder = "";
        if(reminders.size() > 0) {
            for (Reminder reminder : reminders) {
                listReminder += "- " + reminder.getDate() + " " + reminder.getTime() + "\n";
            }
        }
        return listReminder;
    }


}
