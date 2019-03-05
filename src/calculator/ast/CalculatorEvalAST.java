package calculator.ast;

import calculator.ast.ASTVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Deque;
import java.util.Queue;
import java.util.List;
import java.util.Map;

public class CalculatorEvalAST extends ASTVisitor<Double> {
    // Note: variable map data persists as long as program is running (in different instances)
    // Make of type Node
    private static Deque<Map<String, Node>> scopes = new LinkedList<Map<String, Node>>();
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
    public Double visit(PrintExpr node) {
        Node printNode = node.getValue();
        Double value = visit(printNode);
        if (!readExpression) System.out.println(value);
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
        if (scopes.peek().containsKey(functionName)) {
            Node declaration = scopes.peek().get(functionName);
            if (declaration instanceof Function) {
                Function functionDeclaration = (Function) declaration;
                scopes.push(functionDeclaration.getLocalScope());
                for (int i = 0; i < parameters.size(); i++) {
                    String parameter = functionDeclaration.getParameters().get(i);
                    Number parameterValue = new Number(visit(parameters.get(i)));
                    functionDeclaration.defineParameters(parameter, parameterValue);
                }
                Queue<Node> exprNodeQueue = functionDeclaration.getExprNodeQueue();
                for (Node expr : exprNodeQueue) {
                    visit(expr);
                }
                Double returnValue = visit(functionDeclaration.getReturnExpression());
                scopes.pop();
                return returnValue;
            }
        }
        return Double.NaN;
    }

    @Override
    public Double visit(VariableDefinition node) {
        String variableName = node.getDeclarationName();
        Node value = node.getDeclarationValue();
        if (scopes.isEmpty()) scopes.push(new HashMap<String, Node>());
        Map<String, Node> currentScope = scopes.peek();
        currentScope.put(variableName, new Number(visit(value)));
        scopes.pop();
        scopes.push(currentScope);
        return Double.NaN;
    }

    @Override
    public Double visit(UnaryMinusExpression node) {
        Node value = node.getValue();
        return -1.0 * visit(value);
    }

    @Override
    public Double visit(PowerExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return Math.pow(visit(left), visit(right));
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
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) != 0.0 && (double) visit(right) != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(OrExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) != 0.0 || (double) visit(right) != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(MultiplyExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return visit(left) * visit(right);
    }

    @Override
    public Double visit(DivideExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return visit(left) / visit(right);
    }

    @Override
    public Double visit(AddExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return visit(left) + visit(right);
    }

    @Override
    public Double visit(SubtractExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return visit(left) - visit(right);
    }

    @Override
    public Double visit(Number node) {
        return node.getValue();
    }

    @Override
    public Double visit(Variable node) {
        if (scopes.isEmpty()) return Double.NaN;
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
        while (!node.isEmpty()) {
            if (!firstNode) System.out.print(", ");
            firstNode = false;
            Node first = node.remove();
            if (first instanceof Text) {
                String text = ((Text) first).getValue();
                System.out.print(text);
            }
            else {
                System.out.print(visit(first));
            }
        }
        System.out.println("");
        return Double.NaN;
    }

    @Override
    public Double visit(GreaterThanExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) > (double) visit(right) ? 1.0 : 0.0;
    }

    @Override
    public Double visit(GreaterThanOrEqualToExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) >= (double) visit(right) ? 1.0 : 0.0;
    }

    @Override
    public Double visit(LessThanExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) < (double) visit(right) ? 1.0 : 0.0;
    }

    @Override
    public Double visit(LessThanOrEqualToExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) <= (double) visit(right) ? 1.0 : 0.0;
    }

    @Override
    public Double visit(EqualToExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) == (double) visit(right) ? 1.0 : 0.0;
    }

    @Override
    public Double visit(NotEqualToExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return (double) visit(left) != (double) visit(right) ? 1.0 : 0.0;
    }

    @Override
    public Double visit(ErrorNode node) {
        return Double.NaN;
    }
}