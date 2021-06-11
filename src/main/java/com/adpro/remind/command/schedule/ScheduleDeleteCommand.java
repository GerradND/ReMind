package com.adpro.remind.command.schedule;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Schedule;
import com.adpro.remind.service.ScheduleService;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ScheduleDeleteCommand implements Command {

    private ScheduleService scheduleService;
    private String outputMsg;

    public ScheduleDeleteCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public String getOutputMsg() {
        return outputMsg;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb = new EmbedBuilder();
        String idGuild = message.getGuild().getId();
        try {
            int idSchedule = Integer.parseInt(inputContent[2]);
            Schedule schedule = scheduleService.getScheduleByID(idSchedule);

            if (schedule == null || !schedule.getGuild().getIdGuild().equals(idGuild)) {
                outputMsg = ":warning: Schedule tidak ditemukan/ID yang Anda masukan salah. Silahkan coba lagi!";
                eb.setColor(Color.orange);
            } else {
                scheduleService.deleteSchedule(idSchedule, idGuild);
                outputMsg = ":negative_squared_cross_mark: Schedule " + schedule.getTitle() + " berhasil dihapus!";
                eb.setColor(Color.green);
            }

            eb.setTitle(outputMsg);
            message.getChannel().sendMessage(eb.build()).queue();


        } catch (NumberFormatException e) {
            eb.setColor(Color.red);
            outputMsg = "Tolong masukan ID yang valid.";
            eb.addField(outputMsg, "", false);
            message.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
