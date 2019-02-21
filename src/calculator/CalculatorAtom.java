package calculator;

class Atom implements Expression { }

class Number extends Atom {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    public final double getNumber() { return this.value; }
}