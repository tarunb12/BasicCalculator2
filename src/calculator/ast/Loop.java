package calculator.ast;

import java.util.Map;

public class Loop extends Statement {
    private final Node condition;
    private final ExprNodeQueue exprNodeQueue;
    private Map<String, Node> localScopeDefinitions;

    public Loop(Node condition, ExprNodeQueue exprNodeQueue, Map<String, Node> localScopeDefinitions) {
        this.condition = condition;
        this.exprNodeQueue = exprNodeQueue;
        this.localScopeDefinitions = localScopeDefinitions;
    }

    public final Node getCondition() { return this.condition; }
    public final ExprNodeQueue getExprNodeQueue() { return this.exprNodeQueue; }
    public final Map<String, Node> getLocalScopeDefinitions() { return this.localScopeDefinitions; }
}

class WhileLoop extends Loop {
    public WhileLoop(Node condition, ExprNodeQueue exprNodeQueue, Map<String, Node> localScopeDefinitions) {
        super(condition, exprNodeQueue, localScopeDefinitions);
    }
}

class ForLoop extends Loop {
    private final Node variableDefinition;
    private final Node redefinition;

    public ForLoop(Node variableDefinition, Node condition, Node redefinition, ExprNodeQueue exprNodeQueue, Map<String, Node> localScopeDefinitions) {
        super(condition, exprNodeQueue, localScopeDefinitions);
        this.variableDefinition = variableDefinition;
        this.redefinition = redefinition;
    }

    public final Node getVariableDefinition() { return this.variableDefinition; }
    public final Node getRedefinition() { return this.redefinition; }
}