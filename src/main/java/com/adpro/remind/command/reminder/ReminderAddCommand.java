package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Guild;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderAddCommand implements Command {

    private TaskService taskService;
    private GuildService guildService;
    private String[] inputContent;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderAddCommand(TaskService taskService, GuildService guildService){
        this.taskService = taskService;
        this.guildService = guildService;
    }

    private Task newTask(Guild guild){
        String name = inputContent[2];
        String dateText = inputContent[3];
        String timeText = inputContent[4];

        LocalDate date = LocalDate.parse(dateText, dateFormatter);
        LocalTime time = LocalTime.parse(timeText, timeFormatter);

        Task task = new Task(name, date, time, guild);
        taskService.createTask(task);

        return task;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Guild guild = guildService.getGuildByID(message.getGuild().getId());
        this.inputContent = inputContent;
        Task createdTask = newTask(guild);

        String output = "Tugas " + createdTask.getName() + " berhasil dibuat.";
        message.getChannel().sendMessage(output).queue();
    }
}
