package com.adpro.remind.command.reminder;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Task;
import com.adpro.remind.service.TaskService;
import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ReminderDetailCommand implements Command {
    private final TaskService taskService;
    EmbedBuilder embedOutput;

    public ReminderDetailCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    public EmbedBuilder getEmbedOutput(Task task) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Detail Tugas [#" + task.getIdTask() + "]");

        embedBuilder.addField(":id:  Id:", task.getIdTask().toString(), true);
        embedBuilder.addField(":notepad_spiral:  Nama: ", task.getName(), true);
        embedBuilder.addBlankField(true);

        embedBuilder.addField(":date:  Tanggal: ", task.getDate().toString(), true);
        embedBuilder.addField(":hourglass:  Jam: ", task.getTime().toString(), true);
        embedBuilder.addBlankField(true);

        embedBuilder.addField(":bell:  Reminder yang telah dipasang: ", task.getAllReminders(), false);

        embedBuilder.setColor(Color.YELLOW);

        return embedBuilder;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Integer idTask = Integer.parseInt(inputContent[2]);
        Task task = taskService.detailTask(idTask);

        embedOutput = getEmbedOutput(task);

        message.getChannel().sendMessage(embedOutput.build()).queue();
    }
}
