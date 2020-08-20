package org.example.junit5.tested;

import org.assertj.core.api.SoftAssertions;

public class CustomSoftAssertionsProvider extends SoftAssertions {
	
	BuggyCalculatorAssert assertThat(BuggyCalculator actual) {
		return proxy(BuggyCalculatorAssert.class, BuggyCalculator.class, actual);
	}

	ValidCalculatorAssert assertThat(ValidCalculator actual) {
		return proxy(ValidCalculatorAssert.class, ValidCalculator.class, actual);
	}
}
