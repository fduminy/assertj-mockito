package org.example.junit5.tested;

import org.assertj.core.api.SoftAssertions;
import org.assertj.mockito.api.AssertJMockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(AssertJMockitoExtension.class)
public class ValidCalculatorTest {
    @Mock
    private Adder adder;

    @Test
    public void test(SoftAssertions softly) {
        ValidCalculator calculator = new ValidCalculator(adder);
        when(adder.add(1, 2)).thenReturn(3);
        int result = calculator.add(1, 2);

        verify(adder, times(1)).add(1, 2);
        softly.assertThat(result).isEqualTo(3);
        verifyNoMoreInteractions(adder);
    }
}
