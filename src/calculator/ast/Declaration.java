package calculator.ast;

public class Declaration extends Node {
    private final String declarationName;
    private final Node declarationValue;

    public Declaration(String declarationName, Node declararionValue) {
        this.declarationName = declarationName;
        this.declarationValue = declararionValue;
    }

    public final String getDeclarationName() { return this.declarationName; }
    public final Node getDeclarationValue() { return this.declarationValue; }
}

class VariableDefinition extends Declaration {
    public VariableDefinition(String variableName, Node value) {
        super(variableName, value);
    }
}