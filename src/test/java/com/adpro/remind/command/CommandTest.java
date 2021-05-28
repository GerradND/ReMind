package com.adpro.remind.command;

import net.dv8tion.jda.api.entities.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {
    private Class<?> commandClass;
    private Class<?> messageClass;

    @BeforeEach
    public void setup() throws Exception {
        commandClass = Class.forName("com.adpro.remind.command.Command");
        messageClass = Class.forName("net.dv8tion.jda.api.entities.Message");
    }

    @Test
    public void testCommandIsAPublicInterface() {
        int classModifiers = commandClass.getModifiers();

        assertTrue(Modifier.isPublic(classModifiers));
        assertTrue(Modifier.isInterface(classModifiers));
    }

    @Test
    public void testCommandHasGetOutputMessageMethod() throws Exception {
        Class<?> stringInput = String[].class;
        Method getOutputMessage = commandClass.getDeclaredMethod("getOutputMessage", messageClass, stringInput);
        int methodModifiers = getOutputMessage.getModifiers();

        assertTrue(Modifier.isPublic(methodModifiers));
        assertTrue(Modifier.isAbstract(methodModifiers));
        assertEquals(2, getOutputMessage.getParameterCount());
    }
}
