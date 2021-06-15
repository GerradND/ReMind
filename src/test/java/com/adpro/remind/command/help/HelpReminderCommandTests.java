package com.adpro.remind.command.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
public class HelpReminderCommandTests {
    @Mock
    private Message message;

    @Mock
    MessageChannel messageChannel;

    @Mock
    MessageAction messageAction;

    @InjectMocks
    private HelpReminderCommand helpReminderCommand;

    @Test
    public void testHelpCommandOutput() {
        String[] inputContent = {"-help"};

        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);

        helpReminderCommand.getOutputMessage(message, inputContent);

        EmbedBuilder embedOutput = helpReminderCommand.getEmbedOutput();
        MessageEmbed messageEmbed = embedOutput.build();
        assertEquals(messageEmbed.getTitle(), "Reminder Command List");
    }
}
