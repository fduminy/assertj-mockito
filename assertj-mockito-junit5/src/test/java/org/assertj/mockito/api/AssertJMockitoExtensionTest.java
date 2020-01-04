package org.assertj.mockito.api;

import org.assertj.mockito.api.AssertJMockitoExtension.VerificationWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AssertJMockitoExtensionTest {
    @Mock
    private ExtensionContext context;
    @Mock
    private VerificationMode verificationMode;

    @Test
    public void description() {
        assertThrows(IllegalStateException.class, () -> {
            new VerificationWrapper(verificationMode, context).description("test");
        });
        verifyNoInteractions(verificationMode, context);
    }
}