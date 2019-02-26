package calculator.ast;

public abstract class ASTVisitor<T> {
    public abstract T Visit(UnaryMinusExpression node);
    public abstract T Visit(MultiplyExpression node);
    public abstract T Visit(DivideExpression node);
    public abstract T Visit(AddExpression node);
    public abstract T Visit(SubtractExpression node);
    public abstract T Visit(Number node);
    public abstract T Visit(Variable node);
    public abstract T Visit(VariableDeclaration node);

    public T Visit(Node node) {
        if (node instanceof UnaryMinusExpression) return Visit((UnaryMinusExpression) node);
        else if (node instanceof MultiplyExpression) return Visit((MultiplyExpression) node);
        else if (node instanceof DivideExpression) return Visit((DivideExpression) node);
        else if (node instanceof AddExpression) return Visit((AddExpression) node);
        else if (node instanceof SubtractExpression) return Visit((SubtractExpression) node);
        else if (node instanceof Number) return Visit((Number) node);
        else if (node instanceof Variable) return Visit((Variable) node);
        else if (node instanceof VariableDeclaration) return Visit((VariableDeclaration) node);
        else return Visit((Number) node);
    }
}