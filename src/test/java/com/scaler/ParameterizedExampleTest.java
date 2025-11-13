package com.scaler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ParameterizedExampleTest {

    static int[][] data() {
        return new int[][] { { 1 , 2, 2 }, { 5, 3, 15 }, { 121, 4, 484 } };
    }

    @ParameterizedTest(name = "{index} called with: {0}")
    @MethodSource(value =  "data")
    void testWithStringParameter(int[] data) {
        MyClass tester = new MyClass();
        int m1 = data[0];
        int m2 = data[1];
        int expected = data[2];
        assertEquals(expected, tester.multiply(m1, m2));
    }

    // class to be tested
    class MyClass {
        public int multiply(int i, int j) {
            return i * j;
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "WINDOW", "Microsoft Windows [Version 10.?]" ,"This is not windows" })
    void ensureWindowsStringContainWindow(String name) {
        assertTrue(name.toLowerCase().contains("window"));
    }

    @DisplayName("A negative value for year is not supported by the leap year computation.")
    @ParameterizedTest(name = "For example, year {0} is not supported.")
    @ValueSource(ints = { -1, -4 })
    void ensureYear(int year) {
        assertTrue(year < 0);
    }

    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvSource({ "0,    1,   0", "1,    2,   2", "49,  50, 2450", "1,  100, 100" })
    void add(int first, int second, int expectedResult) {
        MyClass calculator = new MyClass();
        assertEquals(expectedResult, calculator.multiply(first, second),
                () -> first + " * " + second + " should equal " + expectedResult);
    }
}