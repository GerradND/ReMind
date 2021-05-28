package com.adpro.remind.controller;

import com.adpro.remind.command.Command;
import com.adpro.remind.repository.CommandRepository;
import com.adpro.remind.repository.CommandRepositoryImpl;
import com.adpro.remind.repository.CommandRepositoryImplTests;
import net.dv8tion.jda.api.entities.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class FeatureCommandImplTest {
    private Class<?> featureCommandImplClass;
    private FeatureCommandImpl featureCommandImplDummy;
    private CommandRepositoryImpl commandRepositoryDummy;
    private Command command;

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

}
