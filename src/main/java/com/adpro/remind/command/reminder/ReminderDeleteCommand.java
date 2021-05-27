package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class ReminderDeleteCommand implements Command {
    private TaskService taskService;

    public ReminderDeleteCommand(TaskService taskService){
        this.taskService = taskService;
    }

    private EmbedBuilder getEmbedOutput(Integer id){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle(":x:  Tugas dengan ID "+ id + " telah dihapus.");
        embedBuilder.setColor(Color.RED);


        return embedBuilder;
    }
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        taskService.deleteTask(idTask);

        EmbedBuilder embedOutput = getEmbedOutput(idTask);
        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
