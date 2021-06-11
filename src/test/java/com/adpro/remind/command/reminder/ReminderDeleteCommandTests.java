package com.adpro.remind.command.reminder;

import com.adpro.remind.service.TaskService;
import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderDeleteCommandTests {
    @Mock
    private Message message;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private TaskService taskService;

    @Mock
    private MessageAction messageAction;

    @InjectMocks
    private ReminderDeleteCommand reminderDeleteCommand;

    @Test
    void testReminderDeleteOutput() {
        String[] inputContent = {"-reminder", "-delete", "1"};

        EmbedBuilder embedOutput = reminderDeleteCommand.getEmbedOutput(1);
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(embedOutput.build())).thenReturn(messageAction);

        reminderDeleteCommand.getOutputMessage(message, inputContent);

        MessageEmbed output = reminderDeleteCommand.embedOutput.build();
        Assertions.assertEquals(":x:  Tugas dengan ID 1 telah dihapus.", output.getTitle());
        Assertions.assertEquals(Color.RED, output.getColor());
    }
}
