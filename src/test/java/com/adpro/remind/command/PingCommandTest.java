package com.adpro.remind.command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PingCommandTest {
    private Class<?> pingCommandClass;
    private Class<?> messageClass;
    private PingCommand dummyPingCommand;

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

}
