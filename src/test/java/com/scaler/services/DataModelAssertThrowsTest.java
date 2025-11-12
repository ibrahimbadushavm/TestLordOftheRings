package com.scaler.services;

import static com.scaler.models.Race.HOBBIT;
import static org.junit.jupiter.api.Assertions.*;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.scaler.models.TolkienCharacter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class DataModelAssertThrowsTest {

    @Test
    @DisplayName("Ensure that access to the fellowship throws exception outside the valid range")
    void exceptionTesting() {
        DataService dataService = new DataService();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> dataService.getFellowship().get(20));
        assertEquals("Index 20 out of bounds for length 9", exception.getMessage());
    }

    @Test
//    @Disabled("Please fix and enable")
    public void ensureThatAgeMustBeLargerThanZeroViaSetter() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        // use assertThrows() rule to check that the message is:
        // Age is not allowed to be smaller than zero
//        frodo.setAge(-1);
        Exception exception= assertThrows(IllegalArgumentException.class,  () -> {
            frodo.setAge(-1);
        });

        assertEquals("Age is not allowed to be smaller than zero", exception.getMessage());

    }

    @Test
//    @Disabled("Please fix and enable")
    public void testThatAgeMustBeLargerThanZeroViaConstructor() {
        // use assertThrows() rule to check that an IllegalArgumentException exception is thrown and
        // that the message is:
        // "Age is not allowed to be smaller than zero"
        Exception exception= assertThrows(IllegalArgumentException.class,  () -> new TolkienCharacter("Frodo", -1, HOBBIT));
        assertEquals("Age is not allowed to be smaller than zero", exception.getMessage());

    }

}