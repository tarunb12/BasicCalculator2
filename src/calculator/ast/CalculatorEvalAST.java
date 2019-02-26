package calculator.ast;

import calculator.ast.ASTVisitor;

public class CalculatorEvalAST extends ASTVisitor<Double> {
    @Override
    public Double Visit(UnaryMinusExpression node) {
        return -1.0 * Visit(node);
    }

    @Override
    public Double Visit(MultiplyExpression node) {
        return Visit(node.getLeft()) * Visit(node.getRight());
    }

    @Override
    public Double Visit(DivideExpression node) {
        return Visit(node.getLeft()) / Visit(node.getRight());
    }

    @Override
    public Double Visit(AddExpression node) {
        return Visit(node.getLeft()) + Visit(node.getRight());
    }

    @Override
    public Double Visit(SubtractExpression node) {
        return Visit(node.getLeft()) - Visit(node.getRight());
    }

    @Override
    public Double Visit(Number node) {
        return node.getValue();
    }

    @Override
    public Double Visit(Variable node) {
        return 0.0;
    }

    @Override
    public Double Visit(VariableDeclaration node) {
        return 0.0;
    }
}