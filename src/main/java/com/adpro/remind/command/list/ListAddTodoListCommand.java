package com.adpro.remind.command.list;


import com.adpro.remind.command.Command;
import com.adpro.remind.model.Guild;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.GuildService;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;

@Component
public class ListAddTodoListCommand implements Command {
    private TodoListService todoListService;
    private GuildService guildService;
    public Message message;

    public ListAddTodoListCommand(TodoListService todoListService, GuildService guildService){
       this.todoListService = todoListService;
       this.guildService = guildService;
   }

   @Override
   public void getOutputMessage(Message message, String[] inputContent){
       this.message = message;
       Guild guild = new Guild(message.getGuild().getId());
       String namaList = inputContent[2];
       todoListService.addTodoList(new TodoList(namaList, guild));
       message.reply(String.format("TodoList %s telah ditambahkan", namaList)).queue();
   }

    @Scheduled(fixedRate = 1000)
    public void sendNotificationToReadTodoList(){
        if (message==null) return;
        Guild guild = new Guild(message.getGuild().getId());
        EmbedBuilder eb = new EmbedBuilder();
        Iterable<TodoList> todoLists = todoListService.showAllTodoList(guild);
        eb.setColor(Color.GREEN);
        eb.setTitle("See Your TodoList(s)\n(ID, Name)");
        for(TodoList todoList : todoLists){
            eb.addField("", String.format("%d %s", todoList.getId(), todoList.getTitle()), false);
        }
       message.reply(eb.build()).queue();
    }

}
