package org.example.tested;

class BuggyCalculator {
    private final Adder adder;
    private final Substractor substractor;

    BuggyCalculator(Adder adder, Substractor substractor) {
        this.adder = adder;
        this.substractor = substractor;
    }

    @SuppressWarnings("unused")
    int add(int number1, int number2) {
        return 5;
    }

    @SuppressWarnings("unused")
    int substract(int number1, int number2) {
        return 123;
    }
}
