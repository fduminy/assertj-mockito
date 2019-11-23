package org.example.tested;

import org.assertj.mockito.api.AssertJMockitoSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;
import static org.mockito.junit.MockitoJUnit.rule;

public class ValidCalculatorTest {
    @Rule
    public AssertJMockitoSoftAssertions softly = new AssertJMockitoSoftAssertions();
    @Rule
    public MockitoRule rule = rule();

    @Mock
    private Adder adder;

    @Test
    public void test() {
        ValidCalculator calculator = new ValidCalculator(adder);
        when(adder.add(1, 2)).thenReturn(3);
        int result = calculator.add(1, 2);

        verify(adder, times(1)).add(1, 2);
        softly.assertThat(result).isEqualTo(3);
        verifyNoMoreInteractions(adder);
    }
}
