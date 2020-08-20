package org.example.junit5.tested;

import org.assertj.core.api.AbstractAssert;

class BuggyCalculatorAssert extends AbstractAssert<BuggyCalculatorAssert, BuggyCalculator>{
    BuggyCalculatorAssert(BuggyCalculator actual) {
        super(actual, BuggyCalculatorAssert.class);
    }

    BuggyCalculatorAssert isBuggy() {
    	return this;
    }

    BuggyCalculatorAssert isValid() {
    	throw new AssertionError("Expecting class to be valid, but it is buggy");
    }
}
