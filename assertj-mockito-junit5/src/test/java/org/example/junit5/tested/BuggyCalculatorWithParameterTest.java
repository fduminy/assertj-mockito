package org.example.junit5.tested;

import org.assertj.core.api.SoftAssertions;
import org.assertj.mockito.api.AssertJMockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(AssertJMockitoExtension.class)
public class BuggyCalculatorWithParameterTest {
    @Mock
    private Adder adder;
    @Mock
    private Substractor substractor;

    @ParameterizedTest
    @EnumSource(Value.class)
    public void test(Value value, SoftAssertions softly) {
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
