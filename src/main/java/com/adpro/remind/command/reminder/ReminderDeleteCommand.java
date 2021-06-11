package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.service.TaskService;
import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ReminderDeleteCommand implements Command {
    private final TaskService taskService;
    EmbedBuilder embedOutput;

    public ReminderDeleteCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    public EmbedBuilder getEmbedOutput(Integer id) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle(":x:  Tugas dengan ID " + id + " telah dihapus.");
        embedBuilder.setColor(Color.RED);

        return embedBuilder;
    }


    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        taskService.deleteTask(idTask);

        embedOutput = getEmbedOutput(idTask);
        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
