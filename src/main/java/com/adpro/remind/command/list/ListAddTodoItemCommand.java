package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ListAddTodoItemCommand implements Command {
    private TodoListService todoListService;

    public ListAddTodoItemCommand(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        int idList = Integer.parseInt(inputContent[2]);
        String namaItem = inputContent[3];
        TodoList todoList = todoListService.addTodoItem(idList, new TodoItem(namaItem));

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        eb.setTitle("TodoItem Added Successfully");
        eb.addField("", String.format("Id TodoList: %d\nTitle TodoList: %s\n(ID Item, Name)", todoList.getId(), todoList.getTitle()), false);
        for (TodoItem todoItem : todoList.getTodoItemSet()) {
            eb.addField("", String.format("%d %s", todoItem.getId(), todoItem.getName()), false);
        }
        message.reply(eb.build()).queue();
    }
}
