package com.adpro.remind.event;

import com.adpro.remind.controller.FeatureCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputEventListenerTests {

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
    public void testOnMessageReceivedMethodExist() throws NoSuchMethodException {
        Method method = inputEventListenerClass.getDeclaredMethod("onMessageReceived", MessageReceivedEvent.class);
        int methodModifiers = method.getModifiers();
        assertTrue(Modifier.isPublic(methodModifiers));
    }


}
