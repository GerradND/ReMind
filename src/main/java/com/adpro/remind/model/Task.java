package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToMany(targetEntity = Reminder.class, mappedBy = "task", cascade = CascadeType.ALL,
                fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reminder> reminders = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_guild")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Guild guild;

    public Task(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void setReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    public String getAllReminders() {
        StringBuilder listReminder = new StringBuilder();
        if(reminders.size() > 0) {
            for (Reminder reminder : reminders) {
                listReminder.append("- ").append(reminder.getDate()).append(" ").append(reminder.getTime()).append("\n");
            }
        }
        return listReminder.toString();
    }


}
