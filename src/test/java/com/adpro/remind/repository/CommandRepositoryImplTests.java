package com.adpro.remind.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.adpro.remind.command.Command;
import com.adpro.remind.command.PingCommand;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandRepositoryImplTests {
    private Class<?> commandRepositoryClass;

    @BeforeEach
    public void setup() throws Exception {
        commandRepositoryClass = Class.forName("com.adpro.remind.repository.CommandRepositoryImpl");
    }

    @Test
    public void testCommandRepositoryHasGetCommandMethod() throws Exception {
        Method getCommand = commandRepositoryClass.getDeclaredMethod("getCommand", String.class);
        int methodModifiers = getCommand.getModifiers();

        assertTrue(Modifier.isPublic(methodModifiers));
        assertEquals(1, getCommand.getParameterCount());
        assertEquals("com.adpro.remind.command.Command",
                getCommand.getGenericReturnType().getTypeName());

    }

    @Test
    public void testCommandRepositoryHasAddCommandMethod() throws Exception {
        Method getCommand = commandRepositoryClass.getDeclaredMethod("addCommand", String.class, Command.class);
        int methodModifiers = getCommand.getModifiers();

        assertTrue(Modifier.isPublic(methodModifiers));
        assertEquals(2, getCommand.getParameterCount());
        assertEquals("void",
                getCommand.getGenericReturnType().getTypeName());

    }

    @Test
    public void testGetCommandMethod() throws Exception {
        CommandRepositoryImpl commandRepository = new CommandRepositoryImpl();
        commandRepository.addCommand("ping", new PingCommand());
        Command pingCommand = commandRepository.getCommand("ping");
        assertEquals(pingCommand.getClass(), PingCommand.class);
    }

}
