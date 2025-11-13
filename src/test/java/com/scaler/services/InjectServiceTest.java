package com.scaler.services;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;


import org.junit.jupiter.api.Test;

import javax.inject.Inject;

class InjectServiceTest {

    @Test
    void ensureJSR330Constructor() {
        int count = 0;
        Constructor<?>[] constructors = InjectService.class.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Inject annotation = constructor.getAnnotation(Inject.class);
            if (annotation != null) {
                count++;
            }
        }
        assertEquals(2, count);
    }

}