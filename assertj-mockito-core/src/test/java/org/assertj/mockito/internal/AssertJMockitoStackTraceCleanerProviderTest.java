package org.assertj.mockito.internal;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.exceptions.stacktrace.StackTraceCleaner;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.junit.MockitoJUnit.rule;

public class AssertJMockitoStackTraceCleanerProviderTest {
    @Rule
    public MockitoRule mockitoRule = rule();

    @Mock
    private StackTraceCleaner defaultCleaner;

    @Test
    public void getStackTraceCleaner() {
        StackTraceCleaner cleaner = new AssertJMockitoStackTraceCleanerProvider().getStackTraceCleaner(defaultCleaner);
        assertThat(cleaner).isInstanceOf(AssertJMockitoStackTraceCleaner.class);
    }
}