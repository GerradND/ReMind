package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderSetCommand implements Command {
    private TaskService taskService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderSetCommand(TaskService taskService){
        this.taskService = taskService;
    }

    private Reminder newReminder(Integer time, String type, String idTask, String idChannel){
        Integer id = Integer.parseInt(idTask);
        Task task = taskService.findByIDTask(id);

        LocalDate taskDate = task.getDate();
        LocalTime taskHour = task.getTime();
        LocalDateTime taskTime = LocalDateTime.of(taskDate, taskHour);

        LocalDateTime reminderTime = getReminderDateTime(taskTime, time, type);
        Reminder reminder = new Reminder(reminderTime.toLocalDate(), reminderTime.toLocalTime(), idChannel);

        taskService.setReminder(reminder, task);
        return reminder;
    }

    private LocalDateTime getReminderDateTime(LocalDateTime taskTime, Integer time, String type){
        LocalDateTime reminderTime = taskTime;
        switch(type.toUpperCase()){
            case "HARI":
                reminderTime = taskTime.minusDays(time);
                break;
            default:
                reminderTime = taskTime.minusHours(time);
        }

        return reminderTime;

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
        Integer time = Integer.parseInt(inputContent[3]);
        String type = inputContent[4];
        String idChannel = message.getChannel().getId();

        Reminder createdReminder = newReminder(time, type, id, idChannel);
        EmbedBuilder embedOutput = getEmbedOutput(id, createdReminder);

        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
