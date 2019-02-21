package calculator;

//
// Statements
//

//
// Variable Assignment
//
class VariableDeclaration implements Statement {
    private final String variableName;
    private final Expression value;

    public VariableDeclaration(String variableName, Expression value) {
        this.variableName = variableName;
        this.value = value;
    }

    public final String getVariableName() { return this.variableName; }
    public final Expression getValue() { return this.value; }
}

//
// Print
//
class PrintStatement implements Statement {
    private final Expression value;

    public PrintStatement(Expression value) {
        this.value = value;
    }

    public final Expression getValue() { return this.value; }
}