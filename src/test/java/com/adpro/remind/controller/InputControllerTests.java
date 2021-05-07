package com.adpro.remind.controller;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InputControllerTests {
    private Class<?> inputControllerClass;

    @InjectMocks
    private InputController inputController1;

    @InjectMocks
    private InputController inputController2;

    @InjectMocks
    private InputController inputController3;

    @InjectMocks
    private InputController inputController4;

    @InjectMocks
    private MessageCreateEvent event;

    @BeforeEach
    public void setup() throws Exception {
        inputControllerClass = Class.forName(
                "com.adpro.remind.controller.InputController");
        String dummy1 = "-reminder ADD dummy";
        String dummy2 = "-schedule ADD dummy";
        String dummy3 = "-list ADD dummy";
        String dummy4 = "-help dummy";
        inputController1 = new InputController(event, dummy1.split(" "));
        inputController2 = new InputController(event, dummy2.split(" "));
        inputController3 = new InputController(event, dummy3.split(" "));
        inputController4 = new InputController(event, dummy4.split(" "));
    }

    /*@Test
    public void testInputControllerHasgetOutputMessageMethod() throws Exception {
        Method getOutputMessage = inputControllerClass.getDeclaredMethod("getOutputMessage", String.class);
        int methodModifiers = getOutputMessage.getModifiers();
        assertTrue(Modifier.isPublic(methodModifiers));
    }*/

    @Test
    public void testInputControllerGetOutputMessageWithReminderType() {
        String result = inputController1.getOutputMessage();
        assertEquals("", result);
    }
    @Test
    public void testInputControllerGetOutputMessageWithScheduleType() {
        String result = inputController2.getOutputMessage();
        assertEquals("", result);
    }
    @Test
    public void testInputControllerGetOutputMessageWithListType() {
        String result = inputController3.getOutputMessage();
        assertEquals("", result);
    }
    @Test
    public void testInputControllerGetOutputMessageWithHelpType() {
        String result = inputController4.getOutputMessage();
        assertEquals("", result);
    }

}
