package calculator.ast;

import java.util.Map;

public class Block extends Node {
    private final ExprNodeQueue exprNodeQueue;
    private Map<String, Node> localScopeDefinitions;

    public Block(ExprNodeQueue exprNodeQueue, Map<String, Node> localScopeDefinitions) {
        this.exprNodeQueue = exprNodeQueue;
        this.localScopeDefinitions = localScopeDefinitions;
    }

    public final ExprNodeQueue getExprNodeQueue() { return this.exprNodeQueue; }
    public final Map<String, Node> getLocalScopeDefinitions() { return this.localScopeDefinitions; }
}

class IfBlock extends Block {
    private final Node condition;

    public IfBlock(ExprNodeQueue exprNodeQueue, Node condition, Map<String, Node> localScopeDefinitions) {
        super(exprNodeQueue, localScopeDefinitions);
        this.condition = condition;
    }

    public final Node getCondition() { return this.condition; }
}

class ElseBlock extends Block {
    public ElseBlock(ExprNodeQueue exprNodeQueue, Map<String, Node> localScopeDefinitions) {
        super(exprNodeQueue, localScopeDefinitions);
    }
}