package org.example.junit5.tested;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.assertj.mockito.api.AssertJMockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

@ExtendWith(AssertJMockitoExtension.class)
public class BuggyCalculatorWithCustomSoftAssertionsProviderTest {
    @Mock
    private Adder adder;
    @Mock
    private Substractor substractor;

    @Test
    public void test(CustomSoftAssertionsProvider softly) {
        BuggyCalculator calculator = new BuggyCalculator(adder, substractor);
        int result = calculator.add(1, 2);

        verify(adder).add(1, 2);
        softly.assertThat(result).isEqualTo(3);
        softly.assertThat(calculator).isBuggy();
        softly.assertThat(calculator).isValid();
        verifyNoMoreInteractions(adder);
        verifyNoInteractions(substractor);
    }
}
