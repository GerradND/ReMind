package com.adpro.remind.command.list;


import com.adpro.remind.command.Command;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Arrays;


public class ListAddTodoListCommand implements Command {
   private TodoListService todoListService;

   public ListAddTodoListCommand(TodoListService todoListService){
       this.todoListService = todoListService;
   }

   @Override
   public MessageEmbed getOutputMessage(Message message, String[] inputContent){
       System.out.println(Arrays.toString(inputContent));
       String namaList = inputContent[2];
       todoListService.addTodoList(new TodoList(namaList));
       message.reply(String.format("TodoList %s telah ditambahkan", namaList)).queue();
       return null;
   }
}
