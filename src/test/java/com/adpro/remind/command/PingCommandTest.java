package com.adpro.remind.command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PingCommandTest {
    private Class<?> pingCommandClass;
    private Class<?> messageClass;
    private PingCommand dummyPingCommand;

    @InjectMocks
    private PingCommand pingCommand;

    @BeforeEach
    public void setUp() throws Exception {
        pingCommandClass = Class.forName("com.adpro.remind.command.PingCommand");
        messageClass = Class.forName("net.dv8tion.jda.api.entities.Message");
    }

    @Test
    public void testPingCommandIsConcreteClass() {
        assertFalse(Modifier.
                isAbstract(pingCommandClass.getModifiers()));
    }

    @Test
    public void testPingCommandIsACommand() {
        Collection<Type> interfaces = Arrays.asList(pingCommandClass.getInterfaces());

        assertTrue(interfaces.stream()
                .anyMatch(type -> type.getTypeName()
                        .equals("com.adpro.remind.command.Command")));
    }

    @Test
    public void testPingCommandOverrideGetOuputMessageMethod() throws Exception {
        Class<?> stringInput = String[].class;
        Method getOutputMessage = pingCommandClass.getDeclaredMethod("getOutputMessage", messageClass, stringInput);

        assertEquals(2,
                getOutputMessage.getParameterCount());
        assertTrue(Modifier.isPublic(getOutputMessage.getModifiers()));
    }
/*
    @Test
    public void testGetOutputMessageSuccess() {
        long time = System.currentTimeMillis();
        String[] messageContent = {"-ping"};
        Message message = mock(Message.class);
        MessageChannel messageChannel = mock(MessageChannel.class);
        MessageAction messageAction = mock(MessageAction.class);

        when(message.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(anyString())).thenReturn(messageAction);

        pingCommand.getOutputMessage(message, messageContent);

        verify(message, times(1)).getChannel();
        verify(messageChannel, times(1)).sendMessage(any(String.class));
        verify(messageAction, times(1)).queue();
    }
 */

}
