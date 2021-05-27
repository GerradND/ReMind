package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class ListShowTodoListCommand implements Command {
    private TodoListService todoListService;

    public ListShowTodoListCommand(TodoListService todoListService){
        this.todoListService = todoListService;
    }

    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        TodoList todoList = todoListService.showTodoList(Integer.parseInt(inputContent[2]));
        if (todoList != null) {
            eb.setTitle(String.format("TodoList %s\n(IDItem, NameItem)", todoList.getTitle()));
            for (TodoItem todoItem : todoList.getTodoItemSet()) {
                eb.addField("", String.format("%d %s", todoItem.getId(), todoItem.getName()), false);
            }
            message.reply(eb.build()).queue();
        }
        return null;
    }
}