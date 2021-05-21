package com.adpro.remind.core.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class NotifyReminder {
    @Scheduled(cron = "0 * * * * ?")
    public void NotifyReminder() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        String strDate = sdf.format(now);
//        Reminder[] reminders = getReminderFromDB();
//        for (Reminder reminder : reminders) {
//            if (strDate.equals(reminder.getDate())) {
//                // ToDo: send reminder to user then delete reminder from DB.
//            }
//        }
    }
}