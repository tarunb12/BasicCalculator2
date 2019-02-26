package calculator.ast;

public class Atom extends Expression { }

class Variable extends Atom {
    private final String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public final String getVariableName() { return this.variableName; }
}

class Number extends Atom {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    public final double getValue() { return this.value; }
}

class Parenthesis extends Atom {
    private final Node expression;

    public Parenthesis(Node expression) {
        this.expression = expression;
    }

    public final Node getExpression() { return this.expression; }
}