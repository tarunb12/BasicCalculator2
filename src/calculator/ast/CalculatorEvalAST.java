package calculator.ast;

import calculator.ast.ASTVisitor;

public class CalculatorEvalAST extends ASTVisitor<Double> {
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
        // Check scope maps
        return 0.0;
    }

    @Override
    public Double visit(Parenthesis node) {
        Node expression = node.getExpression();
        return visit(expression);
    }

    @Override
    public Double visit(VariableDeclaration node) {
        // Insert into appropriate scope map
        return 0.0;
    }
}