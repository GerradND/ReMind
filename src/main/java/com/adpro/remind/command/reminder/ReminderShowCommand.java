package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;

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

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Iterable<Task> listTasks= getListTasks(inputContent[2]);
        String output = "Tugas yang telah ditambahkan: \n";

        for(Task task: listTasks){
            output += "-ID: " + task.getIdTask() + "\n" +
                        "Nama Tugas: " + task.getName() + "\n " +
                        "\n";
        }

        message.getChannel().sendMessage(output).queue();
    }
}
