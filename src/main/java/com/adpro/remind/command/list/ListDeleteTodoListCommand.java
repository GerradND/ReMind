package com.adpro.remind.command.list;

import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.entities.Message;

public class ListDeleteTodoListCommand implements Command {
    private TodoListService todoListService;

    public ListDeleteTodoListCommand(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @Override
    public void getOutputMessage(Message message, String[]inputContent) {
        TodoList todoList = todoListService.deleteTodoList(Integer.parseInt(inputContent[2]));
        if (todoList != null) {
            message.reply(String.format("TodoList %s telah dihapus", todoList.getTitle())).queue();
        }
    }
}