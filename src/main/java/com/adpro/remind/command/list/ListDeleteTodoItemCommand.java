package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ListDeleteTodoItemCommand implements Command {
    private TodoListService todoListService;

    public ListDeleteTodoItemCommand(TodoListService todoListService){
        this.todoListService = todoListService;
    }

    @Override
    public MessageEmbed getOutputMessage(Message message, String[] inputContent){
        TodoItem todoItem = todoListService.deleteTodoItem(Integer.parseInt(inputContent[2]), Integer.parseInt(inputContent[3]));
        if(todoItem != null){
            message.reply(String.format("TodoItem %s pada TodoList %s telah dihapus", todoItem.getName(), todoItem.getTodoList().getTitle())).queue();
        }
        return null;
    }
}