package org.assertj.mockito.internal;

import org.mockito.exceptions.stacktrace.StackTraceCleaner;
import org.mockito.plugins.StackTraceCleanerProvider;

public class AssertJMockitoStackTraceCleanerProvider implements StackTraceCleanerProvider {
    @Override
    public StackTraceCleaner getStackTraceCleaner(StackTraceCleaner defaultCleaner) {
        return new AssertJMockitoStackTraceCleaner(defaultCleaner);
    }
}
