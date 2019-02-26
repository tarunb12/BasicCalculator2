package calculator.ast;

public class Atom extends Expression { }

class Variable extends Node {
    private final String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public final String getVariableName() { return this.variableName; }
}

class Number extends Node {
    public double value = 0.0;

    public Number(double value) {
        this.value = value;
    }

    public final double getValue() { return this.value; }
}