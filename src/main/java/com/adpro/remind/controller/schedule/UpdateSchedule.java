package com.adpro.remind.controller.schedule;

import com.adpro.remind.controller.OpsCommand;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateSchedule extends OpsCommand {

    private String[] components;

    @Autowired
    private ScheduleService scheduleService;


    public UpdateSchedule(String[] components){
        this.components = components;
    }

    public Schedule updatedTimeSchedule() {
        Schedule oldSchedule = scheduleService.getScheduleByID(Integer.parseInt(components[2]));
        oldSchedule.setDay(components[3]);
        oldSchedule.setStartTime(getTime(components[4]));
        oldSchedule.setEndTime(getTime(components[5]));

        return oldSchedule;
    }

    public LocalTime getTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return LocalTime.parse(time, formatter);
    }

    @Override
    public String execute() {
        String message = "";
        Schedule updatedSchedule = this.updatedTimeSchedule();
        scheduleService.updateSchedule(Integer.parseInt(components[2]), updatedSchedule);
        message = "Schedule " + updatedSchedule.getTitle() + " berhasil ter-update!";
        return message;
    }
}
