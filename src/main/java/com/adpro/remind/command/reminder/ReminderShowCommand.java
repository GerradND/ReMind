package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReminderShowCommand implements Command {
    private TaskService taskService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReminderShowCommand(TaskService taskService){
        this.taskService = taskService;
    }

    private Iterable<Task> getListTasks(String type, String idGuild){
        switch (type.toUpperCase()){
            case "ALL":
                return taskService.showAllTask(idGuild);
            default:
                LocalDate date = LocalDate.parse(type, dateFormatter);
                return taskService.showTaskAtDate(date, idGuild);
        }
    }


    private EmbedBuilder getEmbedOutput(Iterable<Task> listTasks){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Tugas yang telah dibuat: ");
        embedBuilder.setColor(Color.CYAN);

        for(Task task:listTasks){
            embedBuilder.addField(":id:", task.getIdTask().toString(), true);
            embedBuilder.addField(":notepad_spiral: Nama Tugas: ", task.getName(), true);
            embedBuilder.addBlankField(true);
        }
        return embedBuilder;
    }

    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent) {
        String idGuild = message.getGuild().getId();
        Iterable<Task> listTasks= getListTasks(inputContent[2], idGuild);
        EmbedBuilder embedOutput = getEmbedOutput(listTasks);

        return embedOutput.build();
    }
}
