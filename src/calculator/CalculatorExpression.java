package calculator;


class UnaryMinusExpression implements Expression {
    private final Expression value;

    public UnaryMinusExpression(Expression value) {
        this.value = value;
    }

    public final Expression getValue() { return this.value; }
}

class VariableReference implements Expression {
    private final String varName;

    public VariableReference(String varName) {
        this.varName = varName;
    }

    public final String getVarName() { return this.varName; }
}

class FloatLiteral implements Expression {
    private final String value;
    
    public FloatLiteral(String value) {
        this.value = value;
    }

    public final String getValue() { return this.value; }
}