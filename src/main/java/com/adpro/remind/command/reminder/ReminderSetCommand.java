package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ReminderSetCommand implements Command {
    private final TaskService taskService;

    EmbedBuilder embedOutput;

    public ReminderSetCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    private Reminder newReminder(Integer time, String type, String idTask, String idChannel) {
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

    private LocalDateTime getReminderDateTime(LocalDateTime taskTime, Integer time, String type) {
        if(type.equalsIgnoreCase("HARI")) {
            taskTime = taskTime.minusDays(time);
        } else {
            taskTime = taskTime.minusHours(time);
        }

        return taskTime;

    }

    public EmbedBuilder getEmbedOutput(String id, Reminder reminder) {
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
        embedOutput = getEmbedOutput(id, createdReminder);

        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
