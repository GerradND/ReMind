package com.adpro.remind.command;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InitCommandTest {
    private Class<?> initCommandClass;

    @BeforeEach
    public void setUp() throws Exception {
        initCommandClass = Class.forName("com.adpro.remind.command.InitCommand");
    }

    @Test
    public void testInitCommandIsConcreteClass() {
        assertFalse(Modifier.isAbstract(initCommandClass.getModifiers()));
    }

}
