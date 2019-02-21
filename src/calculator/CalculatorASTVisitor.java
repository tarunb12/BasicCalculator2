package calculator;

abstract class ASTVisitor<T> {
    public abstract T visit(UnaryMinusExpression node);
    public abstract T visit(MultiplyExpression node);
    public abstract T visit(DivideExpression node);
    public abstract T visit(AddExpression node);
    public abstract T visit(SubtractExpression node);

    public T visit(Node node) {
        if (node instanceof UnaryMinusExpression) return visit((UnaryMinusExpression) node);
        else if (node instanceof MultiplyExpression) return visit((MultiplyExpression) node);
        else if (node instanceof DivideExpression) return visit((DivideExpression) node);
        else if (node instanceof AddExpression) return visit((AddExpression) node);
        else if (node instanceof SubtractExpression) return visit((SubtractExpression) node);
        else return visit(node);
    }
}