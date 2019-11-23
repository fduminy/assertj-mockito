package org.example;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.example.tested.BuggyCalculatorTest;
import org.example.tested.BuggyCalculatorWithTheoryTest;
import org.example.tested.ValidCalculatorTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.assertj.core.api.Assertions.assertThat;

public class SoftAssertionsTest {
    private static final String FAILURE_1 = "-- failure 1 --\n" +
            "Wanted but not invoked:\n" +
            "adder.add(1, 2);\n" +
            "-> at org.example.tested.BuggyCalculatorTest.test(BuggyCalculatorTest.java:28)\n" +
            "Actually, there were zero interactions with this mock.\n";
    private static final String FAILURE_2 = "-- failure 2 --\n" +
            "Expecting:\n" +
            " <5>\n" +
            "to be equal to:\n" +
            " <3>\n" +
            "but was not.\n" +
            "at BuggyCalculatorTest.test(BuggyCalculatorTest.java:29)";
    private static final String THEORY_FAILURE_1 = FAILURE_1.replace("BuggyCalculatorTest", "BuggyCalculatorWithTheoryTest")
            .replace(":28", ":31");
    private static final String THEORY_FAILURE_2 = FAILURE_2.replace("BuggyCalculatorTest", "BuggyCalculatorWithTheoryTest")
            .replace(":29", ":32");

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testBuggyCalculatorTest() {
        Result result = runTest(BuggyCalculatorTest.class);

        softly.assertThat(result.getFailureCount())
                .overridingErrorMessage("There was %d errors instead of one:\n%s", result.getFailureCount(), result.getFailures())
                .isOne();
        if (softly.wasSuccess()) {
            Failure failure = result.getFailures().get(0);
            softly.assertThat(failure.getException())
                    .isExactlyInstanceOf(AssertJMultipleFailuresError.class)
                    .hasMessageContaining("Multiple Failures (2 failures)\n")
                    .hasMessageContaining(FAILURE_1)
                    .hasMessageContaining(FAILURE_2);
        }
    }

    @Test
    public void testBuggyCalculatorWithTheoryTest() {
        Result result = runTest(BuggyCalculatorWithTheoryTest.class);

        softly.assertThat(result.getFailureCount())
                .overridingErrorMessage("There was %d errors instead of one:\n%s", result.getFailureCount(), result.getFailures())
                .isOne();
        if (softly.wasSuccess()) {
            Failure failure = result.getFailures().get(0);
            softly.assertThat(failure.getException()).isExactlyInstanceOf(ParameterizedAssertionError.class);
            if (!softly.wasSuccess()) {
                return;
            }

            softly.assertThat(failure.getException().getCause())
                    .isExactlyInstanceOf(AssertJMultipleFailuresError.class)
                    .hasMessageContaining("Multiple Failures (2 failures)\n")
                    .hasMessageContaining(THEORY_FAILURE_1)
                    .hasMessageContaining(THEORY_FAILURE_2);
        }
    }

    @Test
    public void testValidCalculatorTest() {
        Result result = runTest(ValidCalculatorTest.class);

        assertThat(result.getFailureCount())
                .overridingErrorMessage("There was %d errors instead of none:\n%s", result.getFailureCount(), result.getFailures())
                .isZero();
    }

    private Result runTest(Class<?> classToTest) {
        return new JUnitCore().run(new Computer(), classToTest);
    }
}