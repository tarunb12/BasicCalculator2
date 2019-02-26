package calculator.ast;

public class Expression extends Node { };

class VariableDeclaration extends Node {
    private final String variableName;
    private final Node value;

    public VariableDeclaration(String variableName, Node value) {
        this.variableName = variableName;
        this.value = value;
    }

    public final String getVariableName() { return this.variableName; }
    public final Node getValue() { return this.value; }
}

class UnaryMinusExpression extends Node {
    private final Node value;

    public UnaryMinusExpression(Node value) {
        this.value = value;
    }

    public final Node getValue() { return this.value; }
}