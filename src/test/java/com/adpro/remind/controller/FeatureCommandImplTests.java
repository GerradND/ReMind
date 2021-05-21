package com.adpro.remind.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeatureCommandImplTests {
    private Class<?> featureCommandImplClass;

    @BeforeEach
    public void setUp() throws Exception {
        featureCommandImplClass = Class.forName("com.adpro.remind.controller.FeatureCommandImpl");
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

}
