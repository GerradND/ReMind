package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class ScheduleDeleteCommand implements Command {
    
    private ScheduleService scheduleService;
    
    public ScheduleDeleteCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String outputMsg;
        EmbedBuilder eb = new EmbedBuilder();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            Schedule schedule = scheduleService.getScheduleByID(idSchedule);
            if (schedule == null) {
                outputMsg = ":warning: Schedule tidak ditemukan/ID yang Anda masukan salah. Silahkan coba lagi!";
                eb.setColor(Color.orange);
            } else {
                scheduleService.deleteSchedule(idSchedule);
                outputMsg = ":negative_squared_cross_mark: Schedule " + schedule.getTitle() + " berhasil dihapus!";
                eb.setColor(Color.green);
            }

            eb.setTitle(outputMsg);
            message.getChannel().sendMessage(eb.build()).queue();

        } catch (NumberFormatException e) {
            eb.setColor(Color.red);
            eb.addField("Tolong masukan ID yang valid.", "", false);
            message.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
