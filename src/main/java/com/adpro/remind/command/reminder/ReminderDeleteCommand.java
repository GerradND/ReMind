package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;

public class ReminderDeleteCommand implements Command {
    private TaskService taskService;

    public ReminderDeleteCommand(TaskService taskService){
        this.taskService = taskService;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        taskService.deleteTask(idTask);

        String output = "Tugas berhasil dihapus";
        System.out.println(output);
    }
}
