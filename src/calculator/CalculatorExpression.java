package calculator;

//
// Expressions
//

class UnaryMinusExpression implements Expression {
    private final Expression value;

    public UnaryMinusExpression(Expression value) {
        this.value = value;
    }

    public final Expression getValue() { return this.value; }
}

class VariableReference implements Expression {
    private final String variableName;

    public VariableReference(String variableName) {
        this.variableName = variableName;
    }

    public final String getVariableName() { return this.variableName; }
}

class FloatLiteral implements Expression {
    private final String value;
    
    public FloatLiteral(String value) {
        this.value = value;
    }

    public final String getValue() { return this.value; }
}