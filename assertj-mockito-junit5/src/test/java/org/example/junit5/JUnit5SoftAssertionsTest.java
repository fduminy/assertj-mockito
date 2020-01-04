package org.example.junit5;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.example.junit5.tested.BuggyCalculatorTest;
import org.example.junit5.tested.BuggyCalculatorWithParameterTest;
import org.example.junit5.tested.ValidCalculatorTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;
import static org.junit.platform.launcher.core.LauncherFactory.create;

@ExtendWith(SoftAssertionsExtension.class)
public class JUnit5SoftAssertionsTest {
    private static final int FAILURE_1_LINE = 25;
    private static final int FAILURE_2_LINE = FAILURE_1_LINE + 1;
    private static final String FAILURE_1 = "-- failure 1 --\n" +
            "Wanted but not invoked:\n" +
            "adder.add(1, 2);\n" +
            "-> at org.example.junit5.tested.BuggyCalculatorTest.test(BuggyCalculatorTest.java:" + FAILURE_1_LINE + ")\n" +
            "Actually, there were zero interactions with this mock.\n";
    private static final String FAILURE_2 = "-- failure 2 --\n" +
            "Expecting:\n" +
            " <5>\n" +
            "to be equal to:\n" +
            " <3>\n" +
            "but was not.\n" +
            "at BuggyCalculatorTest.test(BuggyCalculatorTest.java:" + FAILURE_2_LINE + ")";

    private static final int THEORY_FAILURE_1_LINE = 27;
    private static final String THEORY_FAILURE_1 = FAILURE_1.replace("BuggyCalculatorTest", "BuggyCalculatorWithParameterTest")
            .replace(":" + FAILURE_1_LINE, ":" + THEORY_FAILURE_1_LINE);
    private static final String THEORY_FAILURE_2 = FAILURE_2.replace("BuggyCalculatorTest", "BuggyCalculatorWithParameterTest")
            .replace(":" + FAILURE_2_LINE, ":" + (THEORY_FAILURE_1_LINE + 1));

    @Test
    public void testBuggyCalculatorTest(SoftAssertions softly) {
        TestExecutionSummary result = runTest(BuggyCalculatorTest.class);

        softly.assertThat(result.getTestsFailedCount())
                .overridingErrorMessage("There was %d errors instead of one:\n%s", result.getTestsFailedCount(), result.getFailures())
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
    public void testBuggyCalculatorWithParameterTest(SoftAssertions softly) {
        TestExecutionSummary result = runTest(BuggyCalculatorWithParameterTest.class);

        softly.assertThat(result.getTestsFailedCount())
                .overridingErrorMessage("There was %d errors instead of one:\n%s", result.getTestsFailedCount(), result.getFailures())
                .isOne();
        if (softly.wasSuccess()) {
            Failure failure = result.getFailures().get(0);
            softly.assertThat(failure.getException())
                    .isExactlyInstanceOf(AssertJMultipleFailuresError.class)
                    .hasMessageContaining("Multiple Failures (2 failures)\n")
                    .hasMessageContaining(THEORY_FAILURE_1)
                    .hasMessageContaining(THEORY_FAILURE_2);
        }
    }

    @Test
    public void testValidCalculatorTest() {
        TestExecutionSummary result = runTest(ValidCalculatorTest.class);

        assertThat(result.getTestsFailedCount())
                .overridingErrorMessage("There was %d errors instead of none:\n%s", result.getTestsFailedCount(), result.getFailures())
                .isZero();
    }

    private TestExecutionSummary runTest(Class<?> classToTest) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = request().selectors(selectClass(classToTest)).build();
        Launcher launcher = create();
        launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
        return listener.getSummary();
    }
}