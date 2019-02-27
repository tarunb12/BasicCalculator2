package calculator.ast;

import calculator.ast.ASTVisitor;

import java.util.HashMap;
import java.util.Map;

public class CalculatorEvalAST extends ASTVisitor<Double> {

    Map<String, Double> varDefs = new HashMap<String, Double>();

    @Override
    public Double visit(StartNodeQueue node) {
        while (!node.isEmpty()) {
            Node first = node.remove();
            visit(first);
        }
        return 0.0;
    }

    @Override
    public Double visit(PrintExpr node) {
        Node printNode = node.getValue();
        System.out.println(visit(printNode));
        return 0.0;
    }

    @Override
    public Double visit(NewLine node) {
        return 0.0;
    }

    @Override
    public Double visit(VariableDefinition node) {
        String variableName = node.getDeclarationName();
        Node value = node.getDeclarationValue();
        varDefs.put(variableName, visit(value));
        return 0.0;
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
    public Double visit(ExponentialFunction node) {
        Node value = node.getValue();
        return Math.exp(visit(value));
    }

    @Override
    public Double visit(LogarithmFunction node) {
        Node value = node.getValue();
        return Math.log10(visit(value));
    }

    @Override
    public Double visit(SquareRootFunction node) {
        Node value = node.getValue();
        return Math.sqrt(visit(value));
    }

    @Override
    public Double visit(SineFunction node) {
        Node value = node.getValue();
        return Math.sin(visit(value));
    }

    @Override
    public Double visit(CosineFunction node) {
        Node value = node.getValue();
        return Math.cos(visit(value));
    }

    @Override
    public Double visit(NotExpression node) {
        Node value = node.getValue();
        return visit(value) != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(AndExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return visit(left) != 0.0 && visit(right) != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public Double visit(OrExpression node) {
        Node left = node.getLeft();
        Node right = node.getRight();
        return visit(left) != 0.0 || visit(right) != 0.0 ? 1.0 : 0.0;
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
        String variableName = node.getValue();
        if (varDefs.containsKey(variableName)) return varDefs.get(variableName);
        return 0.0;
    }

    @Override
    public Double visit(Text node) {
        return 0.0;
    }

    @Override
    public Double visit(Parenthesis node) {
        Node expression = node.getValue();
        return visit(expression);
    }

    @Override
    public Double visit(StatementNodeQueue node) {
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
        return 0.0;
    }
}