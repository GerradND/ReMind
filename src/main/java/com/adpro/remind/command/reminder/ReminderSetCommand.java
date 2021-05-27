package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderSetCommand implements Command {
    private TaskService taskService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderSetCommand(TaskService taskService){
        this.taskService = taskService;
    }

    private Reminder newReminder(String dateText, String timeText, String idTask, String idChannel){
        LocalDate date = LocalDate.parse(dateText, dateFormatter);
        LocalTime time = LocalTime.parse(timeText, timeFormatter);

        Integer id = Integer.parseInt(idTask);
        Task task = taskService.findByIDTask(id);

        Reminder reminder = new Reminder(date, time, idChannel);
        taskService.setReminder(reminder, task);
        return reminder;
    }

    private EmbedBuilder getEmbedOutput(String id, Reminder reminder){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Reminder untuk tugas dengan ID "+ id + " telah dipasang.");
        embedBuilder.addField(":date:  Tanggal: ", reminder.getDate().toString(), true);
        embedBuilder.addField(":hourglass:  Jam: ", reminder.getTime().toString(), true);
        embedBuilder.addBlankField(true);

        embedBuilder.setColor(Color.GREEN);

        return embedBuilder;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String id =inputContent[2];
        String date = inputContent[3];
        String time = inputContent[4];
        String idChannel = message.getChannel().getId();

        Reminder createdReminder = newReminder(date, time, id, idChannel);
        EmbedBuilder embedOutput = getEmbedOutput(id, createdReminder);

        message.getChannel().sendMessage(embedOutput.build()).queue();

    }
}
