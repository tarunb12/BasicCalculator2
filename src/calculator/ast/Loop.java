package calculator.ast;

public class Loop extends Statement {
    private final Node condition;

    public Loop(Node condition) {
        this.condition = condition;
    }

    public final Node getCondition() { return this.condition; }
}

class WhileLoop extends Loop {
    public WhileLoop(Node condition) {
        super(condition);
    }
}

class ForLoop extends Loop {
    private final Node variableDefinition;
    private final Node redefinition;

    public ForLoop(Node variableDefinition, Node condition, Node redefinition) {
        super(condition);
        this.variableDefinition = variableDefinition;
        this.redefinition = redefinition;
    }

    public final Node getVariableDefinition() { return this.variableDefinition; }
    public final Node getRedefinition() { return this.redefinition; }
}