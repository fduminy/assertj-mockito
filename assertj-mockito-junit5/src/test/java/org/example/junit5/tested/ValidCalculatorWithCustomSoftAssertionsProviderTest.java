package org.example.junit5.tested;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.assertj.mockito.api.AssertJMockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

@ExtendWith(AssertJMockitoExtension.class)
public class ValidCalculatorWithCustomSoftAssertionsProviderTest {
    @Mock
    private Adder adder;

    @Test
    public void test(CustomSoftAssertionsProvider softly) {
        ValidCalculator calculator = new ValidCalculator(adder);
        when(adder.add(1, 2)).thenReturn(3);
        int result = calculator.add(1, 2);

        verify(adder, times(1)).add(1, 2);
        softly.assertThat(result).isEqualTo(3);
        softly.assertThat(calculator).isValid();
        verifyNoMoreInteractions(adder);
    }
}
