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

public class ReminderAddCommand implements Command {

    private final TaskService taskService;
    EmbedBuilder embedOutput;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderAddCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    public Task newTask(String idGuild, String[] inputContent) {
        String name = inputContent[2].replace("\"", "");
        String dateText = inputContent[3];
        String timeText = inputContent[4];

        LocalDate date = LocalDate.parse(dateText, dateFormatter);
        LocalTime time = LocalTime.parse(timeText, timeFormatter);

        Task task = new Task(name, date, time);
        task = taskService.createTask(task, idGuild);

        return task;
    }

    public EmbedBuilder getEmbedOutput(Task task) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Tugas berhasil dibuat!");

        embedBuilder.addField(":id: Id:", task.getIdTask().toString(), true);
        embedBuilder.addField(":notepad_spiral: Nama Tugas: ", task.getName(), true);
        embedBuilder.addBlankField(true);

        embedBuilder.addField(":date: Tanggal: ", task.getDate().toString(), true);
        embedBuilder.addField(":hourglass: Jam: ", task.getTime().toString(), true);
        embedBuilder.addBlankField(true);

        embedBuilder.addField("", "Pasang reminder untuk tugas ini dengan:\n `-reminder set "
                                + task.getIdTask() + " [WAKTU_REMINDER]`", false);

        embedBuilder.setColor(Color.YELLOW);


        return embedBuilder;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String idGuild = message.getGuild().getId();

        Task createdTask = newTask(idGuild, inputContent);
        embedOutput = getEmbedOutput(createdTask);

        message.getChannel().sendMessage(embedOutput.build()).queue();

    }
}
