package com.adpro.remind.command;

import com.adpro.remind.repository.CommandRepositoryImpl;
import com.adpro.remind.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;
import com.adpro.remind.service.ScheduleServiceImpl;
import static org.junit.jupiter.api.Assertions.*;


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
        assertFalse(Modifier.
                isAbstract(initCommandClass.getModifiers()));
    }

}
