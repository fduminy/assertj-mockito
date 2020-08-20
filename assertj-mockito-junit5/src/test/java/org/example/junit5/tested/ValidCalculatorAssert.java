package org.example.junit5.tested;

import org.assertj.core.api.AbstractAssert;

class ValidCalculatorAssert extends AbstractAssert<ValidCalculatorAssert, ValidCalculator>{
    ValidCalculatorAssert(ValidCalculator actual) {
        super(actual, ValidCalculatorAssert.class);
    }

    ValidCalculatorAssert isBuggy() {
    	throw new AssertionError("Expecting class to be buggy, but it is valid");
    }

    ValidCalculatorAssert isValid() {
    	return this;
    }
}
