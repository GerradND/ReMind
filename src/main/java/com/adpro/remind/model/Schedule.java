package com.adpro.remind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
<<<<<<< HEAD
=======
import lombok.NoArgsConstructor;

>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
@Data
<<<<<<< HEAD
=======
@NoArgsConstructor
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4
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

<<<<<<< HEAD
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_guild")
    private Guild guild;
=======
//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "id_user")
//    private User user;
//
//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "id_guild")
//    private Guild guild;
>>>>>>> 2f72d3489f745ec377c15a4e5e4e3ffd915820f4

    public Schedule(String title, DayOfWeek day, LocalTime startTime, LocalTime endTime, String description){
        this.title = title;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
}
