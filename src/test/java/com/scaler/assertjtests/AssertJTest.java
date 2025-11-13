package com.scaler.assertjtests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import com.scaler.models.TolkienCharacter;
import com.scaler.services.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AssertJTest {

    // entry point for all assertThat methods and utility methods (e.g. entry)

    // basic assertions
    private DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = new DataService();
    }

    @Test
    void TestAsserjMethods() {

        TolkienCharacter frodo = dataService.getFellowshipCharacter("Frodo");
        TolkienCharacter sam = dataService.getFellowshipCharacter("Sam");
        TolkienCharacter legolas = dataService.getFellowshipCharacter("Legolas");
        TolkienCharacter aragorn = dataService.getFellowshipCharacter("Aragorn");
        TolkienCharacter boromir = dataService.getFellowshipCharacter("Boromir");

        List<TolkienCharacter> fellowshipOfTheRing = dataService.getFellowship();

        assertThat(frodo.getName()).isEqualToIgnoringCase("Frodo");
        assertThat(frodo).isNotEqualTo("sauron");

        // chaining string specific assertions
        assertThat(frodo.getName()).startsWith("Fro")
                .endsWith("do")
                .isEqualToIgnoringCase("frodo");

        // collection specific assertions (there are plenty more)
// in the examples below fellowshipOfTheRing is a List<TolkienCharacter>
        assertThat(fellowshipOfTheRing).hasSize(9)
                .contains(frodo, sam);

        // as() is used to describe the test and will be shown before the error message
        assertThat(frodo.age).as("check %s's age", frodo.getName()).isEqualTo(33);

        // exception assertion, standard style ...
        assertThatThrownBy(() -> {
            throw new Exception("boom!");
        }).hasMessage("boom!");
        // ... or BDD style
        Throwable thrown = catchThrowable(() -> {
            throw new Exception("boom!");
        });
        assertThat(thrown).hasMessageContaining("boom");

        // using the 'extracting' feature to check fellowshipOfTheRing character's names
        assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getName)
                .doesNotContain("Sauron", "Elrond");

        // extracting multiple values at once grouped in tuples
        assertThat(fellowshipOfTheRing).extracting("name", "age", "race.name")
                .contains(tuple("Boromir", 37, "Man"),
                        tuple("Sam", 38, "Hobbit"),
                        tuple("Legolas", 1000, "Elf"));

        // filtering a collection before asserting
        assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                .containsOnly(aragorn, frodo, legolas, boromir);

        // combining filtering and extraction (yes we can)
        assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                .containsOnly(aragorn, frodo, legolas, boromir)
                .extracting(character -> character.getRace().getName())
                .contains("Hobbit", "Elf", "Man");
    }


// and many more assertions: iterable, stream, array, map, dates, path, file, numbers, predicate, optional ...
}
