package org.assertj.mockito.api;

import org.assertj.mockito.api.AssertJMockitoSoftAssertions.VerificationWrapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.mockito.verification.VerificationMode;

import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.junit.MockitoJUnit.rule;

public class AssertJMockitoSoftAssertionsTest {
    @Rule
    public MockitoRule rule = rule();
    @Rule
    public ExpectedException thrown = none();

    @Mock
    private AssertJMockitoSoftAssertions assertJMockitoSoftAssertions;
    @Mock
    private VerificationMode verificationMode;

    @Test
    public void description() {
        thrown.expect(IllegalStateException.class);
        new VerificationWrapper(assertJMockitoSoftAssertions, verificationMode).description("test");
        verifyNoInteractions(assertJMockitoSoftAssertions, verificationMode);
    }
}