package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ListAddTodoItemCommand implements Command {
    private TodoListService todoListService;

    public ListAddTodoItemCommand(TodoListService todoListService){
        this.todoListService = todoListService;
    }

    @Override
    public void getOutputMessage(Message message, String[] inputContent){
        int idList = Integer.parseInt(inputContent[2]);
        String namaItem = inputContent[3];
        todoListService.addTodoItem(idList, new TodoItem(namaItem));
        message.reply(String.format("TodoItem %s telah ditambahkan", namaItem)).queue();
    }
}
