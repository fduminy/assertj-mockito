package org.assertj.mockito.internal;

import org.assertj.mockito.api.AssertJMockitoSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.exceptions.stacktrace.DefaultStackTraceCleaner;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.junit.MockitoJUnit.rule;

public class AssertJMockitoStackTraceCleanerTest {
    @Rule
    public MockitoRule mockitoRule = rule();

    private AssertJMockitoStackTraceCleaner cleaner;

    @Before
    public void setUp() {
        cleaner = new AssertJMockitoStackTraceCleaner(new DefaultStackTraceCleaner());
    }

    @Test
    public void isInShouldExcludeAPIClasses() {
        assertThat(cleaner.isIn(stackTraceElement(AssertJMockitoSoftAssertions.class))).isFalse();
    }

    @Test
    public void isInShouldExcludeInternalClasses() {
        assertThat(cleaner.isIn(stackTraceElement(AssertJMockitoStackTraceCleaner.class))).isFalse();
    }

    @Test
    public void isInShouldExcludeAssertJCoreAPIClasses() {
        assertThat(cleaner.isIn(stackTraceElement("org.assertj.core.api.AnyClass"))).isFalse();
    }

    @Test
    public void isInShouldExcludeAssertJCoreClasses() {
        assertThat(cleaner.isIn(stackTraceElement("org.assertj.core.AnyClass"))).isFalse();
    }

    @Test
    public void isInShouldIncludeOtherClasses() {
        assertThat(cleaner.isIn(stackTraceElement("org.example"))).isTrue();
    }

    private StackTraceElement stackTraceElement(Class<?> clazz) {
        return stackTraceElement(clazz.getName());
    }

    private StackTraceElement stackTraceElement(String className) {
        return new StackTraceElement(className, "method", className.substring(className.lastIndexOf('.')) + ".java", 1);
    }
}