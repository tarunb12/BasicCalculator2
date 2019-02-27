package calculator.ast;

public class UnaryExpression extends Expression {
    private final Node value;

    public UnaryExpression(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }
}

class UnaryMinusExpression extends UnaryExpression {
    public UnaryMinusExpression(Node value) {
        super(value);
    }
}

class SinFunction extends UnaryExpression {
    public SinFunction(Node value) {
        super(value);
    }
}

class CosFunction extends UnaryExpression {
    public CosFunction(Node value) {
        super(value);
    }
}