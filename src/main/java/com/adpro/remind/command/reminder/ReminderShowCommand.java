package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ReminderShowCommand implements Command {
    private final TaskService taskService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    EmbedBuilder embedOutput;

    public ReminderShowCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    private Iterable<Task> getListTasks(String type, String idGuild) {
        if(type.equalsIgnoreCase("ALL")) {
            return taskService.showAllTask(idGuild);
        } else {
            LocalDate date = LocalDate.parse(type, dateFormatter);
            return taskService.showTaskAtDate(date, idGuild);
        }
    }

    public EmbedBuilder getEmbedOutput(Iterable<Task> listTasks) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Tugas yang telah dibuat: ");
        embedBuilder.setColor(Color.CYAN);

        for(Task task:listTasks) {
            embedBuilder.addField(":id:", task.getIdTask().toString(), true);
            embedBuilder.addField(":notepad_spiral: Nama Tugas: ", task.getName(), true);
            embedBuilder.addBlankField(true);
        }
        return embedBuilder;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        String idGuild = message.getGuild().getId();
        Iterable<Task> listTasks = getListTasks(inputContent[2], idGuild);
        embedOutput = getEmbedOutput(listTasks);

        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
