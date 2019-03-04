package calculator.ast;

public class FunctionExpression extends Expression {
    private final Node value;

    public FunctionExpression(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }

}

class ExponentialFunctionExpression extends FunctionExpression {
    public ExponentialFunctionExpression(Node value) {
        super(value);
    }
}

class LogarithmFunctionExpression extends FunctionExpression {
    public LogarithmFunctionExpression(Node value) {
        super(value);
    }
}

class SquareRootFunctionExpression extends FunctionExpression {
    public SquareRootFunctionExpression(Node value) {
        super(value);
    }
}

class SineFunctionExpression extends FunctionExpression {
    public SineFunctionExpression(Node value) {
        super(value);
    }
}

class CosineFunctionExpression extends FunctionExpression {
    public CosineFunctionExpression(Node value) {
        super(value);
    }
}