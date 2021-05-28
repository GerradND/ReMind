package com.adpro.remind.command.reminder;

import com.adpro.remind.service.TaskService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderDeleteCommandTests {
    @Mock
    Message message;

    @Mock
    TaskService taskService;

    @InjectMocks
    private ReminderDeleteCommand reminderDeleteCommand;

    @Test
    void testReminderDeleteOutput(){
        String[] inputContent = {"-reminder", "-delete", "1"};
        MessageEmbed output = reminderDeleteCommand.getOutputMessage(message, inputContent);
        Assertions.assertEquals(output.getTitle(), ":x:  Tugas dengan ID 1 telah dihapus.");
        Assertions.assertEquals(output.getColor(), Color.RED);
    }
}
