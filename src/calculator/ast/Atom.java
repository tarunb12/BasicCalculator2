package calculator.ast;

public class Atom<T> extends Expression {
    private final T value;

    public Atom(T value) {
        this.value = value;
    }

    public final T getValue() { return this.value; }
}

class Variable extends Atom<String> {
    public Variable(String variableName) {
        super(variableName);
    }
}

class Number extends Atom<Double> {
    public Number(double value) {
        super(value);
    }
}

class Parenthesis extends Atom<Node> {
    public Parenthesis(Node expression) {
        super(expression);
    }
}