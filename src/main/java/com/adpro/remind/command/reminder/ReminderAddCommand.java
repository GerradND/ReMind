package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderAddCommand implements Command {

    private TaskService taskService;
    private String[] inputContent;
    private final String name = inputContent[2];
    private final String dateText = inputContent[3];
    private final String timeText = inputContent[4];

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderAddCommand(TaskService taskService){
        this.taskService = taskService;
    }

    private Task newTask(){
        LocalDate date = LocalDate.parse(dateText, dateFormatter);
        LocalTime time = LocalTime.parse(timeText, timeFormatter);

        Task task = new Task(name, date, time);
        taskService.createTask(task);

        return task;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        this.inputContent = inputContent;
        Task createdTask = newTask();

        String output = "Tugas " + createdTask.getName() + " berhasil dibuat.";
        System.out.println(output);


    }
}
