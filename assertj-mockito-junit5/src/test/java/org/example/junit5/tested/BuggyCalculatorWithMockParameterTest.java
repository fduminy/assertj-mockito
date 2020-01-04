package org.example.junit5.tested;

import org.assertj.core.api.SoftAssertions;
import org.assertj.mockito.api.AssertJMockitoExtension;
import org.example.junit5.tested.BuggyCalculatorWithParameterTest.Value;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class BuggyCalculatorWithMockParameterTest {
    @ExtendWith(AssertJMockitoExtension.class)
    @ParameterizedTest
    @EnumSource(Value.class)
    public void test(Value value, @Mock(name = "adder") Adder adder, @Mock(name = "substractor") Substractor substractor, SoftAssertions softly) {
        BuggyCalculator calculator = new BuggyCalculator(adder, substractor);
        int result = calculator.add(1, value.getValue());

        verify(adder).add(1, value.getValue());
        softly.assertThat(result).isEqualTo(1 + value.getValue());
        verifyNoMoreInteractions(adder);
        verifyNoInteractions(substractor);
    }
}
