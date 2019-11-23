package org.example.tested;

class ValidCalculator {
    private final Adder adder;

    ValidCalculator(Adder adder) {
        this.adder = adder;
    }

    @SuppressWarnings("unused")
    int add(int number1, int number2) {
        return adder.add(number1, number2);
    }
}
