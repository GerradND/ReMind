package com.adpro.remind.controller;

import com.adpro.remind.command.Command;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.repository.CommandRepositoryImpl;
import com.adpro.remind.repository.CommandRepositoryImplTests;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeatureCommandImplTest {
    private Class<?> featureCommandImplClass;
    private FeatureCommandImpl featureCommandImplDummy;
    private CommandRepositoryImpl commandRepositoryDummy;
    private Command command;

    @Mock
    private CommandRepositoryImpl commandRepository;

    @InjectMocks
    private FeatureCommandImpl featureCommand;

    @BeforeEach
    public void setUp() throws Exception {
        featureCommandImplClass = Class.forName("com.adpro.remind.controller.FeatureCommandImpl");
        commandRepositoryDummy = new CommandRepositoryImpl();
        featureCommandImplDummy = new FeatureCommandImpl(commandRepositoryDummy);
    }

    @Test
    public void testFeatureCommandImplIsConcreteClass() {
        assertFalse(Modifier.
                isAbstract(featureCommandImplClass.getModifiers()));
    }

    @Test
    public void testFeatureCommandImplIsAFeatureCommand() {
        Collection<Type> interfaces = Arrays.asList(featureCommandImplClass.getInterfaces());

        assertTrue(interfaces.stream()
                .anyMatch(type -> type.getTypeName()
                        .equals("com.adpro.remind.controller.FeatureCommand")));
    }

    @Test
    public void testFormatCommandWithArrayLengthEqualsOneReturnString() {
        String[] myString = "-help".split(" ");
        assertEquals("help", featureCommandImplDummy.formatCommand(myString));
    }

    @Test
    public void testFormatCommandWithArrayLengthMoreThanOneReturnString() {
        String[] myString = "-help aku".split(" ");
        assertEquals("help aku", featureCommandImplDummy.formatCommand(myString));
    }

    @Test
    public void testOutputMessageSuccess() {
        Message message = mock(Message.class);
        String messageContent = "-help reminder";
        String[] content = messageContent.split(" ");
        String input = "help reminder";
        Command command = mock(Command.class);

        when(commandRepository.getCommand(input)).thenReturn(command);

        featureCommand.outputMessage(message, content);

        verify(commandRepository, times(1)).getCommand(input);
        verify(command).getOutputMessage(message, content);
    }

    @Test
    public void testOutputMessageCatchException() throws NoSuchFieldException, IllegalAccessException {
        Message message = mock(Message.class);
        String messageContent = "-help halo";
        String[] content = messageContent.split(" ");
        String input = "help halo";
        Command command = mock(Command.class);
        MessageChannel messageChannel = mock(MessageChannel.class);
        MessageAction messageAction = mock(MessageAction.class);
        EmbedBuilder eb = mock(EmbedBuilder.class);
        MessageEmbed messageEmbed = mock(MessageEmbed.class);

        when(commandRepository.getCommand(input)).thenReturn(command);
        doThrow(IllegalStateException.class).when(command).getOutputMessage(any(Message.class), any(String[].class));
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);

        featureCommand.outputMessage(message, content);

        verify(commandRepository, times(1)).getCommand(input);
        verify(command).getOutputMessage(message, content);
        verify(message, times(1)).getChannel();
        verify(messageChannel, times(1)).sendMessage(any(MessageEmbed.class));
        verify(messageAction, times(1)).queue();
    }
}
