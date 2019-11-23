package org.example.tested;

import org.assertj.mockito.api.AssertJMockitoSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;
import static org.mockito.junit.MockitoJUnit.rule;

public class BuggyCalculatorTest {
    @Rule
    public AssertJMockitoSoftAssertions softly = new AssertJMockitoSoftAssertions();
    @Rule
    public MockitoRule rule = rule();

    @Mock
    private Adder adder;
    @Mock
    private Substractor substractor;

    @Test
    public void test() {
        BuggyCalculator calculator = new BuggyCalculator(adder, substractor);
        int result = calculator.add(1, 2);

        verify(adder).add(1, 2);
        softly.assertThat(result).isEqualTo(3);
        verifyNoMoreInteractions(adder);
        verifyNoInteractions(substractor);
    }
}
