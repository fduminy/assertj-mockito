package org.assertj.mockito.api;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.util.VisibleForTesting;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationMode;

import static org.mockito.internal.progress.MockingProgressImpl.getDefaultVerificationStrategy;
import static org.mockito.internal.progress.ThreadSafeMockingProgress.mockingProgress;

public class AssertJMockitoSoftAssertions extends JUnitSoftAssertions {
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    mockingProgress().setVerificationStrategy(delegate -> new VerificationWrapper(AssertJMockitoSoftAssertions.this, delegate));
                    AssertJMockitoSoftAssertions.super.apply(base, description).evaluate();
                } finally {
                    // If base.evaluate() throws an error, we must explicitly reset the VerificationStrategy
                    // to prevent subsequent tests to be assert lazily
                    mockingProgress().setVerificationStrategy(getDefaultVerificationStrategy());
                }
            }
        };
    }

    @VisibleForTesting
    static class VerificationWrapper implements VerificationMode {
        private final AssertJMockitoSoftAssertions assertJMockitoSoftAssertions;
        private final VerificationMode delegate;

        @VisibleForTesting
        VerificationWrapper(AssertJMockitoSoftAssertions assertJMockitoSoftAssertions, VerificationMode delegate) {
            this.assertJMockitoSoftAssertions = assertJMockitoSoftAssertions;
            this.delegate = delegate;
        }

        public void verify(VerificationData data) {
            assertJMockitoSoftAssertions.check(() -> this.delegate.verify(data));
        }

        public VerificationMode description(String description) {
            throw new IllegalStateException("Should not fail in this mode");
        }
    }
}
