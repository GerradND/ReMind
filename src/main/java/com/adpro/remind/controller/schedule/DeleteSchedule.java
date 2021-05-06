package com.adpro.remind.controller.schedule;

import com.adpro.remind.controller.OpsCommand;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteSchedule extends OpsCommand {

    private String[] components;

    @Autowired
    private ScheduleService scheduleService;

    public DeleteSchedule(String[] components){
        this.components = components;
    }

    @Override
    public String execute() {
        String message = "";
        if(isInteger(components[2])) {
            if (scheduleService.getScheduleByID(Integer.parseInt(components[2])) == null) {
                message = "Schedule tidak ditemukan/ID yang Anda masukan salah. Silahkan coba lagi!"; //place holder
            } else {
                Schedule schedule = scheduleService.getScheduleByID(Integer.parseInt(components[2]));
                scheduleService.deleteSchedule(schedule.getIdSchedule());
                message = "Schedule " + schedule.getTitle() + " berhasil dihapus!";
            }
        } else {
            message = "Tolong masukan ID yang valid.";
        }
        return message;
    }
}
