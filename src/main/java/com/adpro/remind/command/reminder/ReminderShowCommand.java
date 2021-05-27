package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReminderShowCommand implements Command {
    private TaskService taskService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReminderShowCommand(TaskService taskService){
        this.taskService = taskService;
    }

    private Iterable<Task> getListTasks(String type){
        switch (type.toUpperCase()){
            case "ALL":
                return taskService.showAllTask();
            default:
                LocalDate date = LocalDate.parse(type, dateFormatter);
                return taskService.showTaskAtDate(date);
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
    public void getOutputMessage(Message message, String[] inputContent) {
        Iterable<Task> listTasks= getListTasks(inputContent[2]);
        EmbedBuilder embedOutput = getEmbedOutput(listTasks);

        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
