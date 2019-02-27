package calculator.ast;

public class Function extends Expression {
    private final Node value;

    public Function(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }

}

class ExponentialFunction extends Function {
    public ExponentialFunction(Node value) {
        super(value);
    }
}

class LogarithmFunction extends Function {
    public LogarithmFunction(Node value) {
        super(value);
    }
}

class SquareRootFunction extends Function {
    public SquareRootFunction(Node value) {
        super(value);
    }
}

class SineFunction extends Function {
    public SineFunction(Node value) {
        super(value);
    }
}

class CosineFunction extends Function {
    public CosineFunction(Node value) {
        super(value);
    }
}