package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class ListShowAllTodoListCommand implements Command {
    private TodoListService todoListService;

    public ListShowAllTodoListCommand(TodoListService todoListService){
        this.todoListService = todoListService;
    }

    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent){
        String idGuild = message.getGuild().getId();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        Iterable<TodoList> todoLists = todoListService.showAllTodoList(idGuild);
        eb.setTitle("Daftar TodoList\n(ID, Name)");
        for(TodoList todoList : todoLists){
            eb.addField("", String.format("%d %s", todoList.getId(), todoList.getTitle()), false);
        }
        message.reply(eb.build()).queue();
        return null;
    }
}