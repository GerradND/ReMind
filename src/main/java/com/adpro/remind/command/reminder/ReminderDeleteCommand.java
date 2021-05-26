package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ReminderDeleteCommand implements Command {
    private TaskService taskService;

    public ReminderDeleteCommand(TaskService taskService){
        this.taskService = taskService;
    }

    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        taskService.deleteTask(idTask);

        String output = "Tugas dengan ID: " + idTask + " berhasil dihapus.";
        message.getChannel().sendMessage(output).queue();
        return null;
    }
}
