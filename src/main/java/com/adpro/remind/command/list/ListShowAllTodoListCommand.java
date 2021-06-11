package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ListShowAllTodoListCommand implements Command {
    private TodoListService todoListService;

    public ListShowAllTodoListCommand(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        Guild guild = new Guild(message.getGuild().getId());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        Iterable<TodoList> todoLists = todoListService.showAllTodoList(guild);
        eb.setTitle("Daftar TodoList\n(ID, Name)");
        for (TodoList todoList : todoLists) {
            eb.addField("", String.format("%d %s", todoList.getId(), todoList.getTitle()), false);
        }
        message.reply(eb.build()).queue();
    }
}