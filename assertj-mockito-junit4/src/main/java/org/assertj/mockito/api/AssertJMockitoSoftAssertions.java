package org.assertj.mockito.api;

import org.assertj.core.api.JUnitSoftAssertions;
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
                    mockingProgress().setVerificationStrategy(VerificationWrapper::new);
                    AssertJMockitoSoftAssertions.super.apply(base, description).evaluate();
                } finally {
                    // If base.evaluate() throws an error, we must explicitly reset the VerificationStrategy
                    // to prevent subsequent tests to be assert lazily
                    mockingProgress().setVerificationStrategy(getDefaultVerificationStrategy());
                }
            }
        };
    }

    private class VerificationWrapper implements VerificationMode {
        private final VerificationMode delegate;

        private VerificationWrapper(VerificationMode delegate) {
            this.delegate = delegate;
        }

        public void verify(VerificationData data) {
            check(() -> this.delegate.verify(data));
        }

        public VerificationMode description(String description) {
            throw new IllegalStateException("Should not fail in this mode");
        }
    }
}
