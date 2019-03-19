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
    public Number(Double value) {
        super(value);
    }
}

class Read extends Atom<Double> {
    public Read(Double value) {
        super(value);
    }

    // public final void setValue(Double value) { this.value = value; }
}

class Parenthesis extends Atom<Node> {
    public Parenthesis(Node expression) {
        super(expression);
    }
}

class Text extends Atom<String> {
    public Text(String text) {
        super(text);
    }
}