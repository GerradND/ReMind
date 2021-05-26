package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderUpdateCommand implements Command {
    private TaskService taskService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderUpdateCommand(TaskService taskService){
        this.taskService = taskService;
    }


    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        LocalDate date = LocalDate.parse(inputContent[3], dateFormatter);
        LocalTime time = LocalTime.parse(inputContent[4], timeFormatter);
        Task updatedTask = taskService.updateTask(idTask, date, time);

        String output = "Tugas " + updatedTask.getName() + " berhasil diupdate.";
        message.getChannel().sendMessage(output).queue();
        return null;
    }
}
