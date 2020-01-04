package org.assertj.mockito.internal;

import org.assertj.core.util.VisibleForTesting;
import org.mockito.exceptions.stacktrace.StackTraceCleaner;

class AssertJMockitoStackTraceCleaner implements StackTraceCleaner {
    @VisibleForTesting
    static final String API_PACKAGE = "org.assertj.mockito.api";

    private final StackTraceCleaner defaultCleaner;

    public AssertJMockitoStackTraceCleaner(StackTraceCleaner defaultCleaner) {
        this.defaultCleaner = defaultCleaner;
    }

    @Override
    public boolean isIn(StackTraceElement e) {
        return defaultCleaner.isIn(e)
                && !isInternalClass(e)
                && !isAPIClass(e)
                && !isAssertJCoreClass(e);
    }

    private boolean isAssertJCoreClass(StackTraceElement e) {
        return e.getClassName().startsWith("org.assertj.core.");
    }

    private boolean isAPIClass(StackTraceElement e) {
        return e.getClassName().startsWith(API_PACKAGE);
    }

    private boolean isInternalClass(StackTraceElement e) {
        return e.getClassName().startsWith(AssertJMockitoStackTraceCleanerProvider.class.getPackage().getName() + '.');
    }
}
