package calculator.ast;

import calculator.ast.ASTVisitor;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Deque;
import java.util.Queue;
import java.util.List;
import java.util.Map;

public class CalculatorEvalAST extends ASTVisitor<Double> {
    private Deque<Map<String, Node>> scopes = new LinkedList<Map<String, Node>>();
    private Node nextNode = null;

    private boolean readExpression = false;
    private double currentReadValue = 0.0;

    @Override
    public Double visit(StartNodeQueue node) {
        while (!node.isEmpty()) {
            Node first = node.remove();
            if (!node.isEmpty()) nextNode = node.peek();
            Double value = visit(first);
            if (readExpression) {
                node.remove();
                nextNode = null;
                currentReadValue = 0.0;
                readExpression = false;
                if (first instanceof PrintExpr) System.out.println(value);
            }
        }
        return 0.0;
    }

    @Override
    public Double visit(ExprNodeQueue node) {
        Iterator<Node> it = node.getQueue().iterator();
        while (it.hasNext()) {
            Node first = it.next();
            Double value = visit(first); // calls back to functioncall -> ifelse if recursive
            if (first instanceof Return || first instanceof Statement) return value;
            if (readExpression) {
                it.next();
                nextNode = null;
                currentReadValue = 0.0;
                readExpression = false;
                if (first instanceof PrintExpr) System.out.println(value);
            }
        }
        // returnvalue depends on this ? y
        return 0.0;
    }

    @Override
    public Double visit(PrintExpr node) {
        Node printNode = node.getValue();
        Double value = visit(printNode);
        if (!readExpression && !Double.isNaN(value)) System.out.println(value);
        return value;
    }

    @Override
    public Double visit(Function node) {
        if (scopes.isEmpty()) scopes.add(new HashMap<String, Node>());
        Map<String, Node> currentScope = scopes.peek();
        currentScope.put(node.getFunctionName(), node);
        return Double.NaN;
    }

    @Override
    public Double visit(FunctionCall node) {
        if (scopes.isEmpty()) return Double.NaN;
        String functionName = node.getFunctionName();
        List<Node> parameters = node.getParameters();
        if (parameters.size() != node.getParameters().size()) return Double.NaN;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(functionName)) {
                Node declaration = scope.get(functionName);
                if (declaration instanceof Function) {
                    Function functionDeclaration = (Function) declaration;
                    scopes.push(functionDeclaration.getLocalScope());
                    for (int i = 0; i < parameters.size(); i++) {
                        String parameter = functionDeclaration.getParameters().get(i);
                        Number parameterValue = new Number(visit(parameters.get(i)));
                        functionDeclaration.defineParameters(parameter, parameterValue);
                    }
                    Double returnValue = visit(functionDeclaration.getExprNodeQueue());
                    scopes.pop();
                    return returnValue;
                }    
            }
        }
        return Double.NaN;
    }

    @Override
    public Double visit(Return node) {
        Node returnExpression = node.getReturnValue();
        Double returnValue = visit(returnExpression);
        return returnValue;
    }

    @Override
    public Double visit(IfElseStatement node) {
        Node ifBlockNode = node.getIfBlock();
        IfBlock ifBlock;
        if (!(ifBlockNode instanceof IfBlock)) return Double.NaN;
        else ifBlock = (IfBlock) ifBlockNode;
        Node condition = ifBlock.getCondition();
        Double conditionResult = visit(condition);
        Double returnValue = Double.NaN;
        if (conditionResult != 0.0 && conditionResult != Double.NaN) {
            scopes.push(ifBlock.getLocalScopeDefinitions());
            ExprNodeQueue exprNodeQueue = ifBlock.getExprNodeQueue();
            returnValue = visit(exprNodeQueue);
        }
        else {
            Node elseBlockNode = node.getElseBlock();
            ElseBlock elseBlock;
            if (!(elseBlockNode instanceof ElseBlock)) return Double.NaN;
            else elseBlock = (ElseBlock) elseBlockNode;
            scopes.push(elseBlock.getLocalScopeDefinitions());
            ExprNodeQueue exprNodeQueue = elseBlock.getExprNodeQueue();
            returnValue = visit(exprNodeQueue);
        }
        scopes.pop();
        return returnValue;
    }

    @Override
    public Double visit(ForLoop node) {
        scopes.push(node.getLocalScopeDefinitions());
        Node condition = node.getCondition();
        Node varDef = node.getVariableDefinition();
        Node redef = node.getRedefinition();
        ExprNodeQueue exprNodeQueue = node.getExprNodeQueue();
        visit(varDef);
        while (visit(condition) != 0.0) {
            visit(exprNodeQueue);
            visit(redef);
        }
        scopes.pop();
        return Double.NaN;
    }

    @Override
    public Double visit(WhileLoop node) {
        scopes.push(node.getLocalScopeDefinitions());
        Node condition = node.getCondition();
        while (visit(condition) != 0.0) {
            visit(node.getExprNodeQueue());
        }
        scopes.pop();
        return Double.NaN;
    }

    @Override
    public Double visit(VariableDefinition node) {
        String variableName = node.getDeclarationName();
        Node value = node.getDeclarationValue();
        if (scopes.isEmpty()) scopes.push(new HashMap<String, Node>());
        Iterator<Map<String, Node>> check = scopes.descendingIterator();
        boolean variableExists = false;
        while (check.hasNext()) {
            Map<String, Node> scope = check.next();
            if (scope.containsKey(variableName)) variableExists = true;
        }
        if (variableExists) {
            Iterator<Map<String, Node>> it = scopes.iterator();
            while (it.hasNext()) {
                Map<String, Node> scope = it.next();
                if (scope.containsKey(variableName)) {
                    scope.put(variableName, new Number(visit(value)));
                }
            }
        }
        else {
            Map<String, Node> currentScope = scopes.peek();
            currentScope.put(variableName, new Number(visit(value)));
            scopes.pop();
            scopes.push(currentScope);
        }
        return Double.NaN;    
    }

    @Override
    public Double visit(PowerRedefinition node) {
        String variableName = node.getVariableName();
        Node redefinitionValue = node.getRedefintionValue();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                value = Math.pow(value, visit(redefinitionValue));
                scope.put(variableName, new Number(value));
            }
        }
        return value;
    }

    @Override
    public Double visit(MultiplicationRedefinition node) {
        String variableName = node.getVariableName();
        Node redefinitionValue = node.getRedefintionValue();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                value *= visit(redefinitionValue);
                scope.put(variableName, new Number(value));
            }
        }
        return value;
    }

    @Override
    public Double visit(DivisionRedefinition node) {
        String variableName = node.getVariableName();
        Node redefinitionValue = node.getRedefintionValue();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                value /= visit(redefinitionValue);
                scope.put(variableName, new Number(value));
            }
        }
        return value;
    }

    @Override
    public Double visit(AdditionRedefinition node) {
        String variableName = node.getVariableName();
        Node redefinitionValue = node.getRedefintionValue();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                value += visit(redefinitionValue);
                scope.put(variableName, new Number(value));
            }
        }
        return value;
    }

    @Override
    public Double visit(SubtractionRedefinition node) {
        String variableName = node.getVariableName();
        Node redefinitionValue = node.getRedefintionValue();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                value -= visit(redefinitionValue);
                scope.put(variableName, new Number(value));
            }
        }
        return value;
    }

    @Override
    public Double visit(PreIncrement node) {
        String variableName = node.getVariableName();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                scope.put(variableName, new Number(value + 1));
            }
        }
        return value + 1;
    }

    @Override
    public Double visit(PostIncrement node) {
        String variableName = node.getVariableName();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                scope.put(variableName, new Number(value + 1));
            }
        }
        return value;
    }

    @Override
    public Double visit(PreDecrement node) {
        String variableName = node.getVariableName();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                scope.put(variableName, new Number(value - 1));
            }
        }
        return value - 1;
    }

    @Override
    public Double visit(PostDecrement node) {
        String variableName = node.getVariableName();
        Double value = 0.0;
        if (scopes.isEmpty()) return 0.0;
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) value = ((Number) localDefinition).getValue();
                scope.put(variableName, new Number(value - 1));
            }
        }
        return value;
    }

    @Override
    public Double visit(UnaryMinusExpression node) {
        Node value = node.getValue();
        return -1.0 * visit(value);
    }

    @Override
    public Double visit(PowerExpression node) {
        double[] results = visitBinaryExpression(node);
        return Math.pow(results[0], results[1]);
    }

    @Override
    public Double visit(ExponentialFunctionExpression node) {
        Node value = node.getValue();
        return Math.exp(visit(value));
    }

    @Override
    public Double visit(LogarithmFunctionExpression node) {
        Node value = node.getValue();
        return Math.log10(visit(value));
    }

    @Override
    public Double visit(SquareRootFunctionExpression node) {
        Node value = node.getValue();
        return Math.sqrt(visit(value));
    }

    @Override
    public Double visit(SineFunctionExpression node) {
        Node value = node.getValue();
        return Math.sin(visit(value));
    }

    @Override
    public Double visit(CosineFunctionExpression node) {
        Node value = node.getValue();
        return Math.cos(visit(value));
    }

    @Override
    public Double visit(NotExpression node) {
        Node value = node.getValue();
        return (double) visit(value) == 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(AndExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] != 0.0 && results[1] != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(OrExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] != 0.0 || results[1] != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(MultiplyExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] * results[1];
    }

    @Override
    public Double visit(DivideExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] / results[1];
    }

    @Override
    public Double visit(AddExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] + results[1];
    }

    @Override
    public Double visit(SubtractExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] - results[1];
    }

    @Override
    public Double visit(Number node) {
        return node.getValue();
    }

    @Override
    public Double visit(Variable node) {
        if (scopes.isEmpty()) return 0.0;
        String variableName = node.getValue();
        Iterator<Map<String, Node>> it = scopes.iterator();
        while(it.hasNext()) {
            Map<String, Node> scope = it.next();
            if (scope.containsKey(variableName)) {
                Node localDefinition = scope.get(variableName);
                if (localDefinition instanceof Number) return ((Number) localDefinition).getValue();
            }
        }
        return 0.0;
    }

    @Override
    public Double visit(Read node) {
        readExpression = true;
        if (nextNode == null) return Double.NaN;
        if (currentReadValue == 0.0) currentReadValue = visit(nextNode);
        return currentReadValue;
    }

    @Override
    public Double visit(Text node) {
        return Double.NaN;
    }

    @Override
    public Double visit(Parenthesis node) {
        Node expression = node.getValue();
        return visit(expression);
    }

    @Override
    public Double visit(TextNodeQueue node) {
        boolean firstNode = true;
        Queue<Node> nodeQueue = node.getQueue();
        Iterator<Node> it = nodeQueue.iterator();
        while (it.hasNext()) {
            if (!firstNode) System.out.print(", ");
            firstNode = false;
            Node currentNode = it.next();
            if (currentNode instanceof Text) {
                String text = ((Text) currentNode).getValue();
                System.out.print(text);
            }
            else {
                System.out.print(visit(currentNode));
            }
        }
        System.out.println("");
        return Double.NaN;
    }

    @Override
    public Double visit(GreaterThanExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] > results[1] ? 1.0 : 0.0;
    }

    @Override
    public Double visit(GreaterThanOrEqualToExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] >= results[1] ? 1.0 : 0.0;
    }

    @Override
    public Double visit(LessThanExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] < results[1] ? 1.0 : 0.0;
    }

    @Override
    public Double visit(LessThanOrEqualToExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] <= results[1] ? 1.0 : 0.0;
    }

    @Override
    public Double visit(EqualToExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] == results[1] ? 1.0 : 0.0;
    }

    @Override
    public Double visit(NotEqualToExpression node) {
        double[] results = visitBinaryExpression(node);
        return results[0] != results[1] ? 1.0 : 0.0;
    }

    @Override
    public Double visit(ErrorNode node) {
        return Double.NaN;
    }

    public double[] visitBinaryExpression(BinaryExpression node) {
        Node leftNode = node.getLeft();
        Node rightNode = node.getRight();
        Double left = visit(leftNode);
        Double right = visit(rightNode);
        double[] results = { (double) left, (double) right };
        return results;
    }
}