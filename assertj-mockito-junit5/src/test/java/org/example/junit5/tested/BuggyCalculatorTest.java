package org.example.junit5.tested;

import org.assertj.core.api.SoftAssertions;
import org.assertj.mockito.api.AssertJMockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(AssertJMockitoExtension.class)
public class BuggyCalculatorTest {
    @Mock
    private Adder adder;
    @Mock
    private Substractor substractor;

    @Test
    public void test(SoftAssertions softly) {
        BuggyCalculator calculator = new BuggyCalculator(adder, substractor);
        int result = calculator.add(1, 2);

        verify(adder).add(1, 2);
        softly.assertThat(result).isEqualTo(3);
        verifyNoMoreInteractions(adder);
        verifyNoInteractions(substractor);
    }
}
