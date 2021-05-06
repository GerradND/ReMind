package com.adpro.remind.controller.schedule;
import com.adpro.remind.controller.OpsCommand;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddSchedule extends OpsCommand {

    private String[] components;

    @Autowired
    private ScheduleService scheduleService;

    public AddSchedule(String[] components){
        this.components = components;
    }

    public Schedule formSchedule() {
        return new Schedule(components[2], components[3],
                getTime(components[4]), getTime(components[5]),
                this.formDescription());

    }

    public LocalTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dateTime = LocalTime.parse(time, formatter);

        return dateTime;
    }

    public String formDescription() {
        String description = "";
        for(int i = 6; i < components.length; i++) {
            description += components[i] + " ";
        }
        return description;
    }

    @Override
    public String execute() {
        Schedule newSchedule = this.formSchedule();
        scheduleService.createSchedule(newSchedule);

        return "Schedule " + newSchedule.getTitle() + " berhasil ditambahkan!";
    }
}
