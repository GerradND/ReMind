package com.adpro.remind.command.help;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelpToDoListCommandTests {
    @Mock
    private Message message;

    @Mock
    MessageChannel messageChannel;

    @Mock
    MessageAction messageAction;

    @InjectMocks
    private HelpToDoListCommand helpToDoListCommand;

    @Test
    public void testHelpCommandOutput() {
        String[] inputContent = {"-help"};

        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);

        helpToDoListCommand.getOutputMessage(message, inputContent);

        EmbedBuilder embedOutput = helpToDoListCommand.getEmbedOutput();
        MessageEmbed messageEmbed = embedOutput.build();
        assertEquals(messageEmbed.getTitle(), "ToDoList Command List");
    }
}