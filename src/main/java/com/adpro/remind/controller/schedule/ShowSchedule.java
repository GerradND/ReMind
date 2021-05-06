package com.adpro.remind.controller.schedule;

import com.adpro.remind.controller.OpsCommand;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class ShowSchedule extends OpsCommand {

    private String[] components;
    public final String[] arrHari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

    @Autowired
    private ScheduleService scheduleService;

    public ShowSchedule(String[] components){
        this.components = components;
    }

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }

    @Override
    public String execute() {
        String message = "";
        if (components[2].equals("all")) {
            for (Schedule schedule : scheduleService.getListSchedule()) {
                message += String.format(
                        "ID: %d - %s (%s, %s-%s)",
                        schedule.getIdSchedule(),
                        schedule.getTitle(),
                        schedule.getDay(),
                        schedule.getStartTime().toString(),
                        schedule.getEndTime().toString());
            }

        } else if (isInteger(components[2])) {
            Schedule schedule = scheduleService.getScheduleByID(Integer.parseInt(components[2]));
            message = String.format(
                    "%s (%s, %s-%s)\nDeskripsi: %s",
                    schedule.getTitle(),
                    schedule.getDay(),
                    schedule.getStartTime().toString(),
                    schedule.getEndTime().toString(),
                    schedule.getDescription());

        } else if (stringContainsItemFromList(components[2], arrHari)) { // implement components[2] is a day.
            message += "Schedule untuk hari " + components[2] +":\n";
            for (Schedule schedule : scheduleService.getScheduleByDay(components[2])) {
                message += String.format(
                        "ID: %d - %s (%s-%s)\n",
                        schedule.getIdSchedule(),
                        schedule.getTitle(),
                        schedule.getStartTime().toString(),
                        schedule.getEndTime().toString());
            }

        } else {
            message = "Dummy show schedule.";
        }
        return message;
    }
}
