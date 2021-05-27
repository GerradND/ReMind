package com.adpro.remind.event;

import com.adpro.remind.controller.FeatureCommand;
import com.adpro.remind.service.GuildService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;


@ExtendWith(MockitoExtension.class)
public class InputEventListenerTests {

    @Mock
    private FeatureCommand featureCommand;

    @Mock
    private GuildService guildService;

    @InjectMocks
    private InputEventListener inputEventListener;


    private Class<?> inputEventListenerClass;

    @BeforeEach
    public void setUp() throws Exception {
        inputEventListenerClass = Class.forName("com.adpro.remind.event.InputEventListener");
    }

    @Test
    public void testInputEventListenerIsPublicClass() {
        int classModifiers = inputEventListenerClass.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
        assertTrue(new InputEventListener() instanceof ListenerAdapter);
    }


    @Test
    public void testOnGuildMessageReceivedMethodExist() throws NoSuchMethodException {
        Method method = inputEventListenerClass.getDeclaredMethod("onGuildMessageReceived", GuildMessageReceivedEvent.class);
        int methodModifiers = method.getModifiers();
        assertTrue(Modifier.isPublic(methodModifiers));
    }


    @Test
    public void testAuthorIsBot() {
        GuildMessageReceivedEvent guildMessageReceivedEvent = mock(GuildMessageReceivedEvent.class);
        Message message = mock(Message.class);
        Guild guild = mock(Guild.class);
        String idGuild = "help";
        String messageContent = "-help reminder";
        String[] content = messageContent.split(" ");
        User author = mock(User.class);

        when(guildMessageReceivedEvent.getMessage()).thenReturn(message);
        when(message.getAuthor()).thenReturn(author);
        when(author.isBot()).thenReturn(true);
        when(message.getGuild()).thenReturn(guild);
        when(guild.getId()).thenReturn(idGuild);
        when(message.getContentRaw()).thenReturn(messageContent);

        inputEventListener.onGuildMessageReceived(guildMessageReceivedEvent);

        verify(guildMessageReceivedEvent, times(1)).getMessage();
        verify(message, times(1)).getAuthor();
        verify(author, times(1)).isBot();
        verify(message, times(1)).getGuild();
        verify(guild, times(1)).getId();
        verify(featureCommand, times(0)).outputMessage(message, content);
    }

    @Test
    public void testInputEventListenerCalledFeatureCommandGetOutputMessage() throws NoSuchFieldException, IllegalAccessException {
        GuildMessageReceivedEvent guildMessageReceivedEvent = mock(GuildMessageReceivedEvent.class);
        Message message = mock(Message.class);
        Guild guild = mock(Guild.class);
        String idGuild = "help";
        String messageContent = "-help reminder";
        String[] content = messageContent.split(" ");
        User author = mock(User.class);

        when(guildMessageReceivedEvent.getMessage()).thenReturn(message);
        when(message.getAuthor()).thenReturn(author);
        when(author.isBot()).thenReturn(false);
        when(message.getGuild()).thenReturn(guild);
        when(guild.getId()).thenReturn(idGuild);
        when(message.getContentRaw()).thenReturn(messageContent);

        Field prefixField = InputEventListener.class.getDeclaredField("prefix");
        prefixField.setAccessible(true);
        prefixField.set(inputEventListener, "-");

        inputEventListener.onGuildMessageReceived(guildMessageReceivedEvent);

        verify(guildMessageReceivedEvent, times(1)).getMessage();
        verify(message, times(1)).getAuthor();
        verify(author, times(1)).isBot();
        verify(message, times(1)).getGuild();
        verify(guild, times(1)).getId();
        verify(message, times(2)).getContentRaw();
        verify(guildService).createGuild(idGuild);
        verify(featureCommand, times(1)).outputMessage(message, content);
    }


    @Test
    public void testInputEventListenerException() throws NoSuchFieldException, IllegalAccessException {
        GuildMessageReceivedEvent guildMessageReceivedEvent = mock(GuildMessageReceivedEvent.class);
        Message message = mock(Message.class);
        Guild guild = mock(Guild.class);
        String idGuild = "help";
        User author = mock(User.class);
        MessageChannel messageChannel = mock(MessageChannel.class);
        MessageAction messageAction = mock(MessageAction.class);
        String messageContent = "-help a b";
        String[] content = messageContent.split(" ");

        when(guildMessageReceivedEvent.getMessage()).thenReturn(message);
        when(message.getAuthor()).thenReturn(author);
        when(author.isBot()).thenReturn(false);
        when(message.getGuild()).thenReturn(guild);
        when(guild.getId()).thenReturn(idGuild);
        when(message.getContentRaw()).thenReturn(messageContent);
        doThrow(IllegalStateException.class).when(featureCommand).outputMessage(any(Message.class), any(String[].class));
        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(anyString())).thenReturn(messageAction);

        Field prefixField = InputEventListener.class.getDeclaredField("prefix");
        prefixField.setAccessible(true);
        prefixField.set(inputEventListener, "-");

        inputEventListener.onGuildMessageReceived(guildMessageReceivedEvent);

        verify(guildMessageReceivedEvent, times(1)).getMessage();
        verify(message, times(1)).getAuthor();
        verify(author, times(1)).isBot();
        verify(message, times(1)).getGuild();
        verify(guild, times(1)).getId();
        verify(message, times(3)).getContentRaw();
        verify(featureCommand, times(1)).outputMessage(message, content);
        verify(message, times(1)).getChannel();
        verify(messageChannel, times(1)).sendMessage(anyString());
        verify(messageAction, times(1)).queue();
    }

}
