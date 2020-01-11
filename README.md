[![Build Status](https://travis-ci.org/fduminy/assertj-mockito.svg?branch=master)](https://travis-ci.org/fduminy/assertj-mockito)
[![Coverage Status](https://coveralls.io/repos/github/fduminy/assertj-mockito/badge.svg)](https://coveralls.io/github/fduminy/assertj-mockito)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1d6a4252e9b84219aefa44c733c26618)](https://www.codacy.com/manual/fduminy/assertj-mockito?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fduminy/assertj-mockito&amp;utm_campaign=Badge_Grade)

assertj-mockito is a library integrating [mockito](https://site.mockito.org/) with [AssertJ](https://assertj.github.io/doc/)'s [soft assertions](https://assertj.github.io/doc/#assertj-core-soft-assertions).

Usage

* [JUnit 4](https://junit.org/junit4/)
    * Dependency
        * gradle
            > testCompile 'org.assertj:assertj-mockito-junit4:1.0-SNAPSHOT'
        * maven
            ```xml
            <dependency>
                <groupId>org.assertj</groupId>
                <artifactId>assertj-mockito-junit4</artifactId>
                <version>1.0-SNAPSHOT</version>
                <scope>test</scope>
            </dependency>
            ```
    * Example
        ```java
        public class ValidCalculatorTest {
            @Rule
            public AssertJMockitoSoftAssertions softly = new AssertJMockitoSoftAssertions();
            @Rule
            public MockitoRule rule = rule();
        
            @Mock
            private Adder adder;
        
            @Test
            public void test() {
                ValidCalculator calculator = new ValidCalculator(adder);
                when(adder.add(1, 2)).thenReturn(3);
                int result = calculator.add(1, 2);
        
                verify(adder, times(1)).add(1, 2);
                softly.assertThat(result).isEqualTo(3);
                verifyNoMoreInteractions(adder);
            }
        }        
        ```
      
* [JUnit 5](https://junit.org/junit5/)
    * Dependency
        * gradle
            > testCompile 'org.assertj:assertj-mockito-junit5:1.0-SNAPSHOT'
        * maven
            ```xml
            <dependency>
                <groupId>org.assertj</groupId>
                <artifactId>assertj-mockito-junit5</artifactId>
                <version>1.0-SNAPSHOT</version>
                <scope>test</scope>
            </dependency>
            ```
    * Example
        ```java
        @ExtendWith(AssertJMockitoExtension.class)
        public class ValidCalculatorTest {
            @Mock
            private Adder adder;
        
            @Test
            public void test(SoftAssertions softly) {
                ValidCalculator calculator = new ValidCalculator(adder);
                when(adder.add(1, 2)).thenReturn(3);
                int result = calculator.add(1, 2);
        
                verify(adder, times(1)).add(1, 2);
                softly.assertThat(result).isEqualTo(3);
                verifyNoMoreInteractions(adder);
            }
        }
        ```