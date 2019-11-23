package org.example.tested;

import org.assertj.mockito.api.AssertJMockitoSoftAssertions;
import org.junit.Rule;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;
import static org.mockito.junit.MockitoJUnit.rule;

@RunWith(Theories.class)
public class BuggyCalculatorWithTheoryTest {
    @Rule
    public AssertJMockitoSoftAssertions softly = new AssertJMockitoSoftAssertions();
    @Rule
    public MockitoRule rule = rule();

    @Mock
    private Adder adder;
    @Mock
    private Substractor substractor;

    @Theory
    public void test(Value value) {
        BuggyCalculator calculator = new BuggyCalculator(adder, substractor);
        int result = calculator.add(1, value.value);

        verify(adder).add(1, value.value);
        softly.assertThat(result).isEqualTo(1 + value.value);
        verifyNoMoreInteractions(adder);
        verifyNoInteractions(substractor);
    }

    public enum Value {
        TWO(2);

        private final int value;

        Value(int value) {
            this.value = value;
        }
    }
}
