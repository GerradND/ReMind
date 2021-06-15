package com.adpro.remind.command;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InitCommandTest {
    private Class<?> initCommandClass;
    //private CommandRepositoryImpl commandRepositoryDummy;
    //private ScheduleServiceImpl scheduleServiceDummy;

    @BeforeEach
    public void setUp() throws Exception {
        initCommandClass = Class.forName("com.adpro.remind.command.InitCommand");
        //commandRepositoryDummy = new CommandRepositoryImpl();
        //scheduleServiceDummy = new ScheduleServiceImpl();
    }

    @Test
    public void testInitCommandIsConcreteClass() {
        assertFalse(Modifier.isAbstract(initCommandClass.getModifiers()));
    }

}
