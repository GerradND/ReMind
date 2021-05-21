package com.adpro.remind.event;

import com.adpro.remind.command.help.HelpCommand;
import com.adpro.remind.command.list.ToDoListCommand;
import com.adpro.remind.command.reminder.ReminderCommand;
import com.adpro.remind.command.schedule.ScheduleAddCommand;
import com.adpro.remind.controller.FeatureCommand;
import com.adpro.remind.model.TodoItem;
import com.adpro.remind.model.TodoList;
import com.adpro.remind.service.ScheduleService;
import com.adpro.remind.service.TodoListService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InputEventListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(InputEventListener.class);

    @Value("${prefix}")
    private String prefix;
    private String[] content;
    private Map<String, String> Command = new HashMap<String, String>();

    @Autowired
    private FeatureCommand featureCommand;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TodoListService todoListService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        content = message.getContentRaw().split(" ");

        if (message.getAuthor().isBot()) return;

        if (message.getContentRaw().startsWith(prefix)) {
            if(content.length > 1){
                try {
                    if(content[0].equals("-list") && content[1].equals("ADD")){
                        String namaList = content[2];
                        todoListService.addTodoList(new TodoList(namaList));
                        message.reply(String.format("TodoList %s telah ditambahkan", namaList)).queue();
                    }
                    else if(content[0].equals("-list") && content[1].equals("ADDITEM")){
                        int idList = Integer.parseInt(content[2]);
                        String namaItem = content[3];
                        todoListService.addTodoItem(idList, new TodoItem(namaItem));
                        message.reply(String.format("TodoItem %s telah ditambahkan", namaItem)).queue();
                    }
                    else if(content[0].equals("-list") && content[1].equals("DELETE")){
                        TodoList todoList = todoListService.deleteTodoList(Integer.parseInt(content[2]));
                        if(todoList != null){
                            message.reply(String.format("TodoList %s telah dihapus", todoList.getTitle())).queue();
                        }
                    }
                    else if(content[0].equals("-list") && content[1].equals("DELETEITEM")){
                        TodoItem todoItem = todoListService.deleteTodoItem(Integer.parseInt(content[2]), Integer.parseInt(content[3]));
                        if(todoItem != null){
                            message.reply(String.format("TodoItem %s pada TodoList %s telah dihapus", todoItem.getName(), todoItem.getTodoList().getTitle())).queue();
                        }
                    }
                    else if(content[0].equals("-list") && content[1].equals("SHOW") && content[2].equals("ALL")){
                        Iterable<TodoList> todoLists = todoListService.showAllTodoList();
                        for(TodoList todoList : todoLists){
                            message.reply(String.format("%d %s", todoList.getId(), todoList.getTitle())).queue();
                        }
                    }
                    else if(content[0].equals("-list") && content[1].equals("SHOW")){
                        TodoList todoList = todoListService.showTodoList(Integer.parseInt(content[2]));
                    }

//                    featureCommand.outputMessage(message, content);
                } catch (Exception ex) {
                    message.getChannel().sendMessage(
                            "There was an error in your command..."
                    ).queue();
                    logger.error("Failed to process message: " + message.getContentRaw());
                    ex.printStackTrace();
                }
            }
        }
    }
}
