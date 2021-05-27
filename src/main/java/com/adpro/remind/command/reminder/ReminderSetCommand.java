package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Reminder;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;

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

    private Reminder newReminder(String dateText, String timeText, String idTask){
        LocalDate date = LocalDate.parse(dateText, dateFormatter);
        LocalTime time = LocalTime.parse(timeText, timeFormatter);

        Integer id = Integer.parseInt(idTask);
        Task task = taskService.findByIDTask(id);

        Reminder reminder = new Reminder(date, time);
        taskService.setReminder(reminder, task);
        return reminder;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String id =inputContent[2];
        String date = inputContent[3];
        String time = inputContent[4];

        Reminder createdReminder = newReminder(date, time, id);
        String output = "Reminder pada " + createdReminder.getDate() + " " +
                        createdReminder.getTime() + " berhasil dibuat.";

        message.getChannel().sendMessage(output).queue();

    }
}
