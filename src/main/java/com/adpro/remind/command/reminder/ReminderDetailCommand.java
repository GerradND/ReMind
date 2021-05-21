package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;

public class ReminderDetailCommand implements Command {
    private TaskService taskService;
    public ReminderDetailCommand(TaskService taskService){
        this.taskService = taskService;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        Task task = taskService.detailTask(idTask);

        String output = "Detail tugas [#" + task.getIdTask() + "] \n" +
                        "ID: " + task.getIdTask() + "\n" +
                        "Nama Tugas: " + task.getName() + "\n" +
                        "Tanggal Deadline: " + task.getDate() + "\n" +
                        "Jam Deadline: " + task.getTime() + "\n" +
                        task.getAllReminders();
        System.out.println(output);
    }
}
