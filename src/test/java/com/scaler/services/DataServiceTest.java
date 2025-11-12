package com.scaler.services;

import static com.scaler.models.Race.*;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Map;

import com.scaler.models.Movie;
import com.scaler.models.Ring;
import com.scaler.models.TolkienCharacter;
import org.junit.jupiter.api.*;


class DataServiceTest {

    // TODO initialize before each test
    DataService dataService;

    @BeforeEach
    void TestSetup() {
        dataService = new DataService();
    }

    @Test
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);

        // TODO check that age is 33
        assertEquals(33, frodo.age, "Frodo's age should be 33");
        // TODO check that name is "Frodo"
        assertEquals("Frodo", frodo.getName(), "Frodo's name should be frodo");
        // TODO check that name is not "Frodon"
        assertNotEquals("Frodon", frodo.getName(), "Frodo's name should not be Frodon");
    }

    @Test
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);
        // TODO check that:
        // jake is equal to sameJake
        assertEquals(sameJake, jake, "Jake's name should be same");
        // jake is not equal to jakeClone
        assertNotEquals(jakeClone, jake, "Jake's name should not be same as clone");
    }

    @Test
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().get(0);
        // TODO check that tolkienCharacter.getClass is not a movie class
        assertNotEquals(Movie.class, tolkienCharacter.getClass());
    }

    @Test
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns null for an
        // unknow felllow, e.g., "Lars"
        assertNull(dataService.getFellowshipCharacter("Lars"), "Character Lars should not be found");
    }

    @Test
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns a fellow for an
        // existing felllow, e.g., "Frodo"
        assertNull(dataService.getFellowshipCharacter("Frodo"), "Character Frodo should be found");
    }


    @Test
    void ensureThatFrodoAndGandalfArePartOfTheFellowsip() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO check that Frodo and Gandalf are part of the fellowship
        assertTrue(fellowship.stream().anyMatch(character -> character.getName().equalsIgnoreCase("Frodo")));
        assertTrue(fellowship.stream().anyMatch(character -> character.getName().equalsIgnoreCase("Gandalf")));
    }

    @Test
    void ensureThatOneRingBearerIsPartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();
        Map<Ring, TolkienCharacter> ringbearers = dataService.getRingBearers();
        // TODO test that at least one ring bearer is part of the fellowship
        boolean anyRingBearerInFellowship= ringbearers.values().stream()
                        .anyMatch(fellowship::contains);

        assertTrue(anyRingBearerInFellowship, "At least one ring bearer should be part of the fellowship");
    }

    // TODO Use @RepeatedTest(int) to execute this test 1000 times
    @Tag("slow")
    @DisplayName("Minimal stress testing: run this test 1000 times to ")
    @RepeatedTest(1000)
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    void ensureOrdering() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // ensure that the order of the fellowship is:
        // frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir
        java.util.List<String> expectedOrder = java.util.Arrays.asList(
                "Frodo", "Sam", "Merry", "Pippin", "Gandalf", "Legolas", "Gimli", "Aragorn", "Boromir");

        java.util.List<String> actualOrder = fellowship.stream()
                .map(TolkienCharacter::getName)
                .collect(java.util.stream.Collectors.toList());

        assertEquals(expectedOrder, actualOrder, "Fellowship order should match the expected sequence");
    }

    @Test
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO test ensure that all hobbits and men are younger than 100 years
        boolean isagegreaterthanhundered= fellowship.stream()
                .filter(character->character.getRace().equals(HOBBIT) || character.getRace().equals(MAN))
                        .anyMatch(tolkienCharacter -> tolkienCharacter.age>100);

        boolean isagelessthanhundered= fellowship.stream()
                .filter(character->character.getRace().equals(ELF) || character.getRace().equals(DWARF))
                .anyMatch(tolkienCharacter -> tolkienCharacter.age<100);

        // TODO also ensure that the elfs, dwars the maia are all older than 100 years
        assertAll("Age checks",
                ()-> assertFalse(isagegreaterthanhundered, "All hobbits and Man should be younger than 100 years"),
                ()-> assertFalse(isagelessthanhundered, "All Elfs, Dwars and Maia should be older than 100 years"));

        // HINT fellowship.stream might be useful here
    }

    @Test
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // TODO Write a test to get the 20 element from the fellowship throws an
        // IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> fellowship.get(20),
                "Accessing the 20th element should throw IndexOutOfBoundsException");
    }

    @Test
    void TestLongRunnningMethod_CheckTimeout() {
        // TODO write a test to check that the long running method completes within 3000 milliseconds
        assertTimeoutPreemptively(java.time.Duration.ofMillis(3000), () -> {
            dataService.update();
        }, "The long running method should complete within 3000 milliseconds");
    }

}