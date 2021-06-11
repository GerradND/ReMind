package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ReminderUpdateCommand implements Command {
    private final TaskService taskService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    EmbedBuilder embedOutput;

    public ReminderUpdateCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    public EmbedBuilder getEmbedOutput(Task task) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle(":white_check_mark:  Tugas dengan ID: " + task.getIdTask() + " berhasil diupdate.");

        return embedBuilder;

    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        LocalDate date = LocalDate.parse(inputContent[3], dateFormatter);
        LocalTime time = LocalTime.parse(inputContent[4], timeFormatter);
        Task updatedTask = taskService.updateTask(idTask, date, time);

        embedOutput = getEmbedOutput(updatedTask);
        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
