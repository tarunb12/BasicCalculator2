package calculator.ast;

import java.util.Map;
import java.util.List;
import java.util.Queue;

public class Function extends Node {
    private final String functionName;
    private final List<String> parameters;
    private Map<String, Node> localScopeDefinitions;
    private final ExprNodeQueue exprNodeQueue;

    public Function(String functionName, List<String> parameters, Map<String, Node> localScopeDefinitions, ExprNodeQueue exprNodeQueue) {
        this.functionName = functionName;
        this.parameters = parameters;
        this.localScopeDefinitions = localScopeDefinitions;
        this.exprNodeQueue = exprNodeQueue;
    }

    public final String getFunctionName() { return this.functionName; }
    public final int numberOfParameters() { return this.parameters.size(); }
    public final List<String> getParameters() { return this.parameters; }
    public final void defineParameters(String parameter, Node parameterValue) { localScopeDefinitions.put(parameter, parameterValue); }
    public final Map<String, Node> getLocalScope() { return this.localScopeDefinitions; }
    public final ExprNodeQueue getExprNodeQueue() { return this.exprNodeQueue; }
}

class FunctionCall extends Expression {
    private final String functionName;
    private final List<Node> parameters;

    public FunctionCall(String functionName, List<Node> parameters) {
        this.functionName = functionName;
        this.parameters = parameters;
    }

    public final String getFunctionName() { return this.functionName; }
    public final int numberOfParameters() { return this.parameters.size(); }
    public final List<Node> getParameters() { return this.parameters; }
}

class Return extends Expression {
    private final Node returnValue;

    public Return(Node returnValue) {
        this.returnValue = returnValue;
    }

    public final Node getReturnValue() { return this.returnValue; }
}